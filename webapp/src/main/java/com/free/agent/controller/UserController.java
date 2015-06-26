package com.free.agent.controller;


import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.free.agent.service.SportService;
import com.free.agent.service.UserService;
import com.free.agent.utils.HttpUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Enumeration;
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
    public ModelAndView get(User u, HttpServletRequest request) {
        Set<String> set =HttpUtil.getParams(request,"select");
        userService.save(u,set);
        ModelAndView model = new ModelAndView("login-form");
        return model;
    }

    @RequestMapping(value = "/user/info", method = RequestMethod.GET)
    public ModelAndView getInfo(Principal principal) {
        ModelAndView model = new ModelAndView("info");
        model.addObject("user", userService.findByLogin(principal.getName()));
        return model;
    }
}
