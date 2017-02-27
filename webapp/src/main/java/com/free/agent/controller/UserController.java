package com.free.agent.controller;


import com.free.agent.Response;
import com.free.agent.SocialNetworkProperty;
import com.free.agent.Token;
import com.free.agent.dto.EmailDto;
import com.free.agent.dto.UserDto;
import com.free.agent.dto.UserRegistrationDto;
import com.free.agent.dto.network.FacebookProfile;
import com.free.agent.dto.network.GoogleProfile;
import com.free.agent.dto.network.SocialProfile;
import com.free.agent.dto.network.VkProfile;
import com.free.agent.exception.EmailAlreadyUsedException;
import com.free.agent.exception.EmailDidNotRegisteredException;
import com.free.agent.exception.EmailIsNotDetectedException;
import com.free.agent.exception.WrongLinkException;
import com.free.agent.model.User;
import com.free.agent.service.SportService;
import com.free.agent.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.stream.Collectors;

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

    @Autowired
    @Qualifier("vk")
    private SocialNetworkProperty vkProperty;

    @Autowired
    @Qualifier("google")
    private SocialNetworkProperty googleProperty;

    @Autowired
    @Qualifier("facebook")
    private SocialNetworkProperty facebookProperty;

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
    @ResponseBody
    public String isAuthentication(Principal principal) {
        return principal == null ? Response.ok(false) : Response.ok(true);
    }

    @RequestMapping(value = SAVE_USER, method = RequestMethod.POST)
    @ResponseBody
    public String saveUser(@Valid UserRegistrationDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.error(getAllErrors(bindingResult));
        }
        try {
            userService.save(userDto);
        } catch (EmailAlreadyUsedException e) {
            return Response.error(EMAIL_REGISTERED_ERROR);
        }
        return Response.ok();
    }

    @RequestMapping(value = GET_POSTPONE_EMAIL, method = RequestMethod.POST)
    @ResponseBody
    public String getPostponeEmail(String hash, String key) {
        try {
            return Response.ok(userService.getPostponeEmail(hash, key));
        } catch (WrongLinkException e) {
            return Response.error(ACTIVATED_ERROR);
        }
    }

    @RequestMapping(value = ACTIVATE_USER, method = RequestMethod.GET)
    @ResponseBody
    public String activateUser(String hash, String key) {
        try {
            return Response.ok(userService.activateUser(hash, key));
        } catch (WrongLinkException e) {
            return Response.error(ACTIVATED_ERROR);
        }
    }

    @RequestMapping(value = RESET_PASSWORD, method = RequestMethod.POST)
    @ResponseBody
    public String resetPassword(@Valid EmailDto emailDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.error(getAllErrors(bindingResult));
        }
        try {
            userService.resetPassword(emailDto.getEmail());
            return Response.ok();
        } catch (EmailDidNotRegisteredException e) {
            return Response.error(EMAIL_DID_NOT_REGISTERED_ERROR);
        }
    }

    @RequestMapping(value = EDIT_USER, method = RequestMethod.POST, produces = BaseController.PRODUCES)
    @ResponseBody
    public String editUser(@PathVariable(value = "id") Long id, Principal principal, UserDto userDto, HttpServletRequest request) {
        if (!userService.findById(id).getEmail().equals(principal.getName())) {
            return Response.error(EDIT_PROFILE_ERROR);
        }
        //todo userService.editUser(id, userDto, HttpRequestUtil.getParams(request, "select"));
        userService.editUser(id, userDto, null);
        return Response.ok();
    }

    @RequestMapping(value = INFO_ABOUT_USER, method = RequestMethod.GET, produces = BaseController.PRODUCES)
    @ResponseBody
    public String getInfoAboutUser(Principal principal) {
        return Response.ok(userService.getInfoAboutUser(principal.getName()));
    }

    @RequestMapping(value = GET_USER_BY_ID, method = RequestMethod.GET, produces = BaseController.PRODUCES)
    @ResponseBody
    public String getUserById(@PathVariable(value = "id") Long id) {
        return Response.ok(userService.getInfoAboutUserById(id));

    }

    @RequestMapping(value = GET_IMAGE, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response, @PathVariable(value = "id") Long id) throws IOException {
        User user = userService.findById(id);
        InputStream fis;
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

    @RequestMapping(value = LOGIN_WITH_GOOGLE, method = RequestMethod.GET)
    @ResponseBody
    public String googleLogin(String code, HttpServletRequest request) throws IOException {
        HttpPost httppost = new HttpPost(googleProperty.getToken_url());
        httppost.addHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httppost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                new BasicNameValuePair("code", code),
                new BasicNameValuePair("client_id", googleProperty.getClient_id()),
                new BasicNameValuePair("client_secret", googleProperty.getClient_secret()),
                new BasicNameValuePair("redirect_uri", host + LOGIN_WITH_GOOGLE),
                new BasicNameValuePair("grant_type", "authorization_code")
        )));
        HttpResponse response = HttpClientBuilder.create().build().execute(httppost);
        Token token = new Gson().fromJson(EntityUtils.toString(response.getEntity()), Token.class);
        response = HttpClientBuilder.create().build().execute(new HttpGet(googleProperty.getProfile_url() +
                "access_token=" + token.getAccess_token()));

        return authentication(new GoogleProfile(response.getEntity()), request);
    }

    @RequestMapping(value = REGISTRATION_WITH_GOOGLE, method = RequestMethod.GET)
    @ResponseBody
    public String googleRegistration(String code) throws IOException {
        HttpPost httppost = new HttpPost(googleProperty.getToken_url());
        httppost.addHeader("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE);
        httppost.setEntity(new UrlEncodedFormEntity(Lists.newArrayList(
                new BasicNameValuePair("code", code),
                new BasicNameValuePair("client_id", googleProperty.getClient_id()),
                new BasicNameValuePair("client_secret", googleProperty.getClient_secret()),
                new BasicNameValuePair("redirect_uri", host + REGISTRATION_WITH_GOOGLE),
                new BasicNameValuePair("grant_type", "authorization_code")
        )));
        HttpResponse response = HttpClientBuilder.create().build().execute(httppost);
        Token token = new Gson().fromJson(EntityUtils.toString(response.getEntity()), Token.class);
        response = HttpClientBuilder.create().build().execute(new HttpGet(googleProperty.getProfile_url() +
                "access_token=" + token.getAccess_token()));

        return saveProfile(new GoogleProfile(response.getEntity()));
    }

    @RequestMapping(value = LOGIN_WITH_FACEBOOK, method = RequestMethod.GET)
    @ResponseBody
    public String facebookLogin(String code, HttpServletRequest request) throws IOException {
        HttpResponse response = HttpClientBuilder.create().build().execute(new HttpGet(facebookProperty.getToken_url() +
                "client_id=" + facebookProperty.getClient_id() + "&" +
                "redirect_uri=" + host + LOGIN_WITH_FACEBOOK + "&" +
                "client_secret=" + facebookProperty.getClient_secret() + "&" +
                "code=" + code));
        String token = EntityUtils.toString(response.getEntity()).split("&")[0].split("=")[1];
        return authentication(new FacebookProfile(new FacebookTemplate(token)), request);
    }

    @RequestMapping(value = REGISTRATION_WITH_FACEBOOK, method = RequestMethod.GET)
    @ResponseBody
    public String facebookRegistration(String code) throws IOException {
        HttpResponse response = HttpClientBuilder.create().build().execute(new HttpGet(facebookProperty.getToken_url() +
                "client_id=" + facebookProperty.getClient_id() + "&" +
                "redirect_uri=" + host + REGISTRATION_WITH_FACEBOOK + "&" +
                "client_secret=" + facebookProperty.getClient_secret() + "&" +
                "code=" + code));
        String token = EntityUtils.toString(response.getEntity()).split("&")[0].split("=")[1];

        return saveProfile(new FacebookProfile(new FacebookTemplate(token)));
    }

    @RequestMapping(value = LOGIN_WITH_VK, method = RequestMethod.GET)
    @ResponseBody
    public String vkLogin(String code, HttpServletRequest httpServletRequest) throws IOException {
        HttpResponse response = HttpClientBuilder.create().build().execute(new HttpGet(vkProperty.getToken_url() +
                "client_id=" + vkProperty.getClient_id() + "&" +
                "redirect_uri=" + host + LOGIN_WITH_VK + "&" +
                "client_secret=" + vkProperty.getClient_secret() + "&" +
                "code=" + code + "&" +
                "scope=email"));
        Token token = new Gson().fromJson(EntityUtils.toString(response.getEntity()), Token.class);
        response = HttpClientBuilder.create().build().execute(new HttpGet(vkProperty.getProfile_url() +
                "uids=" + token.getUser_id() + "&" +
                "fields=uid&" +
                "access_token=" + token.getAccess_token()));
        VkProfile profile = new VkProfile(response.getEntity());
        profile.setEmail(token.getEmail());
        return authentication(profile, httpServletRequest);
    }

    @RequestMapping(value = REGISTRATION_WITH_VK, method = RequestMethod.GET)
    @ResponseBody
    public String vkRegistration(String code) throws IOException {
        HttpResponse response = HttpClientBuilder.create().build().execute(new HttpGet(vkProperty.getToken_url() +
                "client_id=" + vkProperty.getClient_id() + "&" +
                "redirect_uri=" + host + REGISTRATION_WITH_VK + "&" +
                "client_secret=" + vkProperty.getClient_secret() + "&" +
                "code=" + code + "&" +
                "scope=email"));
        Token token = new Gson().fromJson(EntityUtils.toString(response.getEntity()), Token.class);
        response = HttpClientBuilder.create().build().execute(new HttpGet(vkProperty.getProfile_url() +
                "uids=" + token.getUser_id() + "&" +
                "fields=uid,first_name,last_name,nickname,screen_name,sex,bdate,city,country,photo_200,verified&" +
                "access_token=" + token.getAccess_token()));
        VkProfile profile = new VkProfile(response.getEntity());
        profile.setCity(getCityById(profile.getCityId()));
        profile.setCountry(getCountryById(profile.getCountryId()));
        profile.setEmail(token.getEmail());

        return saveProfile(profile);
    }


    private String saveProfile(SocialProfile profile) {
        try {
            userService.save(profile);
            return Response.ok();
        } catch (EmailAlreadyUsedException e) {
            return Response.error(EMAIL_REGISTERED_ERROR);
        } catch (EmailIsNotDetectedException e1) {
            return Response.error(EMAIL_IS_NOT_DETECTED_ERROR);
        } catch (IOException e) {
            return Response.error(SAVE_IMAGE_ERROR);
        }
    }

    private String authentication(SocialProfile profile, HttpServletRequest request) {
        try {
            userService.authentication(profile, request);
            return Response.ok();
        } catch (BadCredentialsException e) {
            return Response.error(LOGIN_ERROR);
        } catch (EmailDidNotRegisteredException e1) {
            return Response.error(EMAIL_DID_NOT_REGISTERED_ERROR);
        }
    }

    private String getCountryById(String countryId) throws IOException {
        HttpResponse response = HttpClientBuilder.create().build().execute(
                new HttpGet("http://api.vk.com/method/database.getCountriesById?country_ids=" + countryId));
        return EntityUtils.toString(response.getEntity());
    }

    private String getCityById(String cityId) throws IOException {
        HttpResponse response = HttpClientBuilder.create().build().execute(
                new HttpGet("http://api.vk.com/method/database.getCitiesById?city_ids=" + cityId));
        return EntityUtils.toString(response.getEntity());
    }

    private Integer[] getAllErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().map(input -> Integer.valueOf(input.getDefaultMessage()))
                .collect(Collectors.toList()).toArray(new Integer[bindingResult.getErrorCount()]);
    }

}
