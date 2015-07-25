package com.free.agent.controller;

import com.free.agent.model.Sport;
import com.free.agent.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collection;

/**
 * Created by antonPC on 23.06.15.
 */
@Controller
public class AdminController {

    @Autowired
    private SportService sportService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String getInfo() {
        return "admin";
    }

    @RequestMapping(value = "/admin/sport", method = RequestMethod.GET)
    public Collection<Sport> getInfo1() {
        return sportService.getAllSports();
    }

    @RequestMapping(value = "/admin/sport", method = RequestMethod.POST)
    public Sport getSave(Sport sport) {
        return sportService.save(sport);
    }
}
