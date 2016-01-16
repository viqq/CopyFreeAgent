package com.free.agent.controller;

import com.free.agent.Filter;
import com.free.agent.FilterNew;
import com.free.agent.FreeAgentAPI;
import com.free.agent.Response;
import com.free.agent.model.User;
import com.free.agent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Collection;

/**
 * Created by antonPC on 03.07.15.
 */
@Controller
public class SearchController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String getSearchPage() {
        return "search";
    }

    @RequestMapping(value = "/search/user", method = RequestMethod.GET)
    public Collection<User> getAllUsers() {
        return userService.findAll();
    }

    @RequestMapping(value = FreeAgentAPI.FIND_USER, method = RequestMethod.POST, produces = BaseController.PRODUCES)
    @ResponseBody
    public String findUserByFilter(Filter filter) {
        return Response.ok(userService.findByFilter(filter));
    }

    @RequestMapping(value = "/test/filter", method = RequestMethod.POST, produces = BaseController.PRODUCES)
    @ResponseBody
    public String findUserByFilterTest(Principal principal, FilterNew filter, Integer startIndex) {
        return Response.ok(userService.findByFilter(filter, startIndex, principal.getName()));
    }


}
