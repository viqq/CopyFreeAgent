package com.free.agent.controller;

import com.free.agent.model.Sport;
import com.free.agent.service.SportService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by antonPC on 23.06.15.
 */
@Controller
public class AdminController {

    private final static Logger LOGGER = Logger.getLogger(AdminController.class);

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
        ModelAndView model = new ModelAndView("admin");
        sportService.save(sport);
        model.addObject("sports", sportService.getAllSports());
        return model;
    }
}
