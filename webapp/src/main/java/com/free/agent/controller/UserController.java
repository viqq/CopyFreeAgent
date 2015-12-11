package com.free.agent.controller;


import com.free.agent.Response;
import com.free.agent.Token;
import com.free.agent.dto.UserDto;
import com.free.agent.dto.UserRegistrationDto;
import com.free.agent.exception.EmailAlreadyUsedException;
import com.free.agent.exception.WrongLinkException;
import com.free.agent.model.User;
import com.free.agent.service.SportService;
import com.free.agent.service.UserService;
import com.free.agent.utils.HttpRequestUtil;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;

import static com.free.agent.FreeAgentAPI.*;


/**
 * Created by antonPC on 21.06.15.
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SportService sportService;

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String getBasePage() {
        return "index";
    }

    @RequestMapping(value = GET_ALL_SPORTS, method = RequestMethod.GET, produces = BaseController.PRODUCES)
    @ResponseBody
    public String getAllSports() {
        return Response.ok(sportService.getAllSports());
    }

    @RequestMapping(value = IS_AUTHENTICATION, method = RequestMethod.GET)
    public
    @ResponseBody
    String isAuthentication(Principal principal) {
        return principal == null ? Response.ok(false) : Response.ok(true);
    }

    @RequestMapping(value = SAVE_USER, method = RequestMethod.POST)
    public
    @ResponseBody
    String saveUser(@Valid UserRegistrationDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.error(getAllErrors(bindingResult));
        }
        try {
            userService.save(userDto);
        } catch (EmailAlreadyUsedException e) {
            Response.error(EMAIL_REGISTERED_ERROR);
        }
        return Response.ok();
    }

    @RequestMapping(value = GET_POSTPONE_EMAIL, method = RequestMethod.POST)
    public
    @ResponseBody
    String getPostponeEmail(String hash, String key) {
        try {
            return Response.ok(userService.getPostponeEmail(hash, key));
        } catch (WrongLinkException e) {
            return Response.error(ACTIVATED_ERROR);
        }
    }

    @RequestMapping(value = ACTIVATE_USER, method = RequestMethod.GET)
    public
    @ResponseBody
    String activateUser(String hash, String key) {
        try {
            return Response.ok(userService.activateUser(hash, key));
        } catch (WrongLinkException e) {
            return Response.error(ACTIVATED_ERROR);
        }
    }

    @RequestMapping(value = RESET_PASSWORD, method = RequestMethod.GET)
    public
    @ResponseBody
    String resetPassword(@PathVariable(value = "email") String email) {
        try {
            userService.resetPassword(email);
            return Response.ok();
        } catch (IllegalArgumentException e) {
            return Response.error(EMAIL_DID_NOT_REGISTERED_ERROR);
        }
    }


    //todo only for admin
    @RequestMapping(value = DELETE_USER, method = RequestMethod.DELETE, produces = BaseController.PRODUCES)
    public
    @ResponseBody
    String deleteUser(@PathVariable(value = "id") Long id) {
        userService.deleteUser(id);
        return Response.ok();
    }

    @RequestMapping(value = EDIT_USER, method = RequestMethod.POST, produces = BaseController.PRODUCES)
    public
    @ResponseBody
    String editUser(@PathVariable(value = "id") Long id, Principal principal, UserDto userDto, HttpServletRequest request) {
        if (!userService.findById(id).getEmail().equals(principal.getName())) {
            return Response.error(EDIT_PROFILE_ERROR);
        }
        userService.editUser(id, userDto, HttpRequestUtil.getParams(request, "select"));
        return Response.ok();
    }

    @RequestMapping(value = INFO_ABOUT_USER, method = RequestMethod.GET, produces = BaseController.PRODUCES)
    public
    @ResponseBody
    String getInfoAboutUser(Principal principal) {
        return Response.ok(userService.getInfoAboutUser(principal.getName()));
    }

    @RequestMapping(value = GET_USER_BY_ID, method = RequestMethod.GET, produces = BaseController.PRODUCES)
    public
    @ResponseBody
    String getUserById(@PathVariable(value = "id") Long id) {
        return Response.ok(userService.getInfoAboutUserById(id));

    }

    @RequestMapping(value = GET_IMAGE, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response, @PathVariable(value = "id") Long id) throws IOException {
        User user = userService.findById(id);
        FileInputStream fis;
        if (user.getImage() != null) {
            fis = new FileInputStream(userService.findById(id).getImage() + ".jpg");
        } else {
            fis = new FileInputStream(UserController.class.getClassLoader().getResource("images/freeagent.jpg").getFile());
        }
        ByteStreams.copy(fis, response.getOutputStream());

    }

    @RequestMapping(value = SAVE_IMAGE, method = RequestMethod.POST, produces = BaseController.PRODUCES)
    @ResponseBody
    public String setImage(HttpServletRequest request, Principal principal) {
        try {
            userService.addImage(principal.getName(), request);
            return Response.ok();
        } catch (Exception e) {
            return Response.error(SAVE_IMAGE_ERROR);
        }
    }

    @RequestMapping(value = "/api/gglogin", method = RequestMethod.GET)
    @ResponseBody
    public String googleLogin(String code) throws IOException {


        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpPost httppost = new HttpPost("https://www.googleapis.com/oauth2/v3/token");

        httppost.addHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httppost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                new BasicNameValuePair("code", code),
                new BasicNameValuePair("client_id", "984084695751-v075768i8b3torl2t0evijuj63t4sfrc.apps.googleusercontent.com"),
                new BasicNameValuePair("client_secret", "ycRT8Y45rNwOFIt4d5f9bLNF"),
                new BasicNameValuePair("redirect_uri", host + "/api/gglogin"),
                new BasicNameValuePair("grant_type", "authorization_code")
        )));

        HttpResponse response = httpclient.execute(httppost);
        Token token = new Gson().fromJson(EntityUtils.toString(response.getEntity()), Token.class);
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet("https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + token.getAccess_token());
        response = client.execute(get);

        return EntityUtils.toString(response.getEntity());
    }

    private Integer[] getAllErrors(BindingResult bindingResult) {
        return Collections2.transform(bindingResult.getAllErrors(), new Function<ObjectError, Integer>() {
            @Override
            public Integer apply(ObjectError input) {
                return Integer.valueOf(input.getDefaultMessage());
            }
        }).toArray(new Integer[bindingResult.getErrorCount()]);

    }

}
