package com.free.agent.controller;

import com.free.agent.Filter;
import com.free.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by antonPC on 03.07.15.
 */
@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView get() {
        ModelAndView model = new ModelAndView("search");
        model.addObject("users", userService.findAll());
        return model;
    }


    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ModelAndView getInfo(Filter filter) {
        ModelAndView model = new ModelAndView("search");
        model.addObject("users", userService.findByFilter(filter));
        return model;
    }

}
