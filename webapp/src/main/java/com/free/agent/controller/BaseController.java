package com.free.agent.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by antonPC on 23.06.15.
 */
@ControllerAdvice
public class BaseController {

    @ExceptionHandler(Exception.class)
    public ModelAndView getFilms() {
        System.out.print("Exception!!!");
        ModelAndView model = new ModelAndView("login-form");
        return model;
    }
}
