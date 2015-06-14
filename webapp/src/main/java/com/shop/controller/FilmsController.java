package com.shop.controller;

import com.prauf.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;



@Controller
@RequestMapping("/shop")
public class FilmsController {

    @Autowired
    private FilmService filmService;

    @RequestMapping(value = "/films", method = RequestMethod.GET)
    public ModelAndView getFilms() {
        ModelAndView model = new ModelAndView("films");
        model.addObject("films", filmService.getAllFilms());

        return model;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleAllExceptions(Exception ex) {
        ex.printStackTrace();
        return new ModelAndView("index");
    }

}
