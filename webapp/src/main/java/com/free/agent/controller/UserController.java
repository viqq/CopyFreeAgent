package com.free.agent.controller;


import com.free.agent.dto.UserDto;
import com.free.agent.model.User;
import com.free.agent.service.SportService;
import com.free.agent.service.UserService;
import com.free.agent.utils.HttpRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.Set;


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
    public ModelAndView getFilms() {
        ModelAndView model = new ModelAndView("registration");
        model.addObject("allSports", sportService.getAllSports());
        return model;
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String get(@Valid UserDto userDto, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Set<String> set = HttpRequestUtil.getParams(request, "select");
        userService.save(getUser(userDto), set);
        ModelAndView model = new ModelAndView("login-form");
        return "login-form";
    }

    private User getUser(UserDto userDto) {
        User user = new User(userDto.getLogin(), userDto.getPassword(), userDto.getPhone());
        user.setDescription(userDto.getDescription());
        user.setCity(userDto.getCity());
        user.setDateOfBirth(new Date());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        return user;
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public ModelAndView getInfo(Principal principal) {
        ModelAndView model = new ModelAndView("info");
        model.addObject("user", userService.findByLogin(principal.getName()));
        return model;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView loginFormDef() {
        return new ModelAndView("login-form");
    }

    @RequestMapping(value = "/user-login", method = RequestMethod.GET)
    public ModelAndView loginForm() {
        return new ModelAndView("login-form");
    }

    @RequestMapping(value = "/error-login", method = RequestMethod.GET)
    public ModelAndView invalidLogin() {
        ModelAndView modelAndView = new ModelAndView("login-form");
        modelAndView.addObject("error", true);
        return modelAndView;
    }

}
