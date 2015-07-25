package com.free.agent.controller;

import com.free.agent.Filter;
import com.free.agent.model.User;
import com.free.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * Created by antonPC on 03.07.15.
 */
@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String get() {
        return "search";
    }

    @RequestMapping(value = "/search/user", method = RequestMethod.GET)
    public Collection<User> get2() {
        return userService.findAll();
    }

    @RequestMapping(value = "/search/user", method = RequestMethod.POST)
    public Collection<User> getInfo(Filter filter) {
        return userService.findByFilter(filter);
    }

}
