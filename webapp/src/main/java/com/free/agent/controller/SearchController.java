package com.free.agent.controller;

import com.free.agent.Filter;
import com.free.agent.FreeAgentAPI;
import com.free.agent.Response;
import com.free.agent.model.User;
import com.free.agent.service.UserService;
import com.free.agent.service.dto.UserWithSport;
import com.free.agent.utils.ExtractFunction;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;

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
    public
    @ResponseBody
    String findUserByFilter(Filter filter) {
        List<UserWithSport> list = Lists.newArrayList();
        for (User u : userService.findByFilter(filter)) {
            list.add(ExtractFunction.getUserForUI(u));
        }
        return Response.ok(list);
    }

}
