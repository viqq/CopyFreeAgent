package com.free.agent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by antonPC on 19.06.15.
 */
@Controller
public class SignInController {

    @RequestMapping(value = "/signin", method = RequestMethod.GET)
    public ModelAndView getFilms() {
        ModelAndView model = new ModelAndView("signin");
        return model;
    }
}
