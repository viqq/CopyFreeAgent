package com.free.agent.controller;

import com.free.agent.model.Sport;
import com.free.agent.service.SportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

/**
 * Created by antonPC on 23.06.15.
 */
@Controller
public class AdminController {

    @Autowired
    private SportService sportService;

    @RequestMapping(value = "/admin/sport", method = RequestMethod.GET)
    public ModelAndView getInfo() {
        ModelAndView model = new ModelAndView("admin");
        model.addObject("sports", sportService.getAllSports());
        return model;
    }

    @RequestMapping(value = "/admin/sport", method = RequestMethod.POST)
    public ModelAndView getSave(Sport sport) {
        sportService.save(sport);
        ModelAndView model = new ModelAndView("admin");
        model.addObject("sports", sportService.getAllSports());
        return model;
    }
}
