package com.free.agent.controller;


import com.free.agent.Response;
import com.free.agent.dto.UserDto;
import com.free.agent.service.SportService;
import com.free.agent.service.UserService;
import com.free.agent.utils.ExtractFunction;
import com.free.agent.utils.HttpRequestUtil;
import com.google.common.collect.Collections2;
import com.google.common.io.ByteStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import java.security.Principal;
import java.util.Set;

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

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String getFilms() {
        return "registration";
    }

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String loginFormDe() {
        return "user";
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String loginFormD() {
        return "info";
    }

    @RequestMapping(value = {"/", "/user-login"}, method = RequestMethod.GET)
    public String loginFormDef() {
        return "login-form";
    }

    @RequestMapping(value = {"/error-login"}, method = RequestMethod.GET)
    public
    @ResponseBody
    String loginFormDef1() {
        return Response.error(459);
    }

    @RequestMapping(value = GET_ALL_SPORTS, method = RequestMethod.GET, produces = BaseController.PRODUCES)
    @ResponseBody
    public String getInfo1() {
        return Response.ok(Collections2.transform(sportService.getAllSports(), ExtractFunction.SPORT_NAME_INVOKE));
    }

    @RequestMapping(value = IS_AUTHENTICATION, method = RequestMethod.GET)
    public
    @ResponseBody
    String isAuthentication(Principal principal) {
        return principal == null ? Response.ok(false) : Response.ok(true);
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveUser(@Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return Response.error(VALIDATION_ERROR);
        }
        Set<String> set = HttpRequestUtil.getParams(request, "select");
        userService.save(ExtractFunction.getUser(userDto), set);
        return Response.ok(OK);
    }

    @RequestMapping(value = INFO_ABOUT_USER, method = RequestMethod.GET, produces = BaseController.PRODUCES)
    public
    @ResponseBody
    String getInfoAboutUser(Principal principal) {
        return Response.ok(ExtractFunction.getUserForUI(userService.findByLogin(principal.getName())));
    }

    @RequestMapping(value = GET_USER_BY_ID, method = RequestMethod.GET, produces = BaseController.PRODUCES)
    public
    @ResponseBody
    String getUserById(@PathVariable(value = "id") Long id) {
        return Response.ok(ExtractFunction.getUserForUI(userService.findById(id)));

    }

    @RequestMapping(value = GET_IMAGE, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public void getImage(HttpServletResponse response, @PathVariable(value = "id") Long id) throws IOException {
        ByteStreams.copy(new FileInputStream(userService.findById(id).getImage() + ".jpg"), response.getOutputStream());
    }

    @RequestMapping(value = SAVE_IMAGE, method = RequestMethod.POST, produces = BaseController.PRODUCES)
    public String setImage(HttpServletRequest request, Principal principal) {
        try {
            userService.addImage(principal.getName(), request);
            return Response.ok(OK);
        } catch (Exception e) {
            return Response.error(SAVE_IMAGE_ERROR);
        }
    }

}
