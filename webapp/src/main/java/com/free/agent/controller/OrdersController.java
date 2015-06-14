package com.free.agent.controller;

import com.free.agent.dto.OrderDTO;
import com.free.agent.model.Film;
import com.free.agent.model.Order;
import com.free.agent.service.FilmService;
import com.free.agent.service.OrderService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/shop")
public class OrdersController {

    @Autowired
    private FilmService filmService;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/createOrder", method = RequestMethod.GET)
    public ModelAndView createOrder(@RequestParam("filmId") Integer filmId) {
        ModelAndView model = new ModelAndView("order");
        model.addObject("film", filmService.getFilm(filmId));
        return model;
    }

    @RequestMapping(value = "/saveOrder", method = RequestMethod.POST)
    public ModelAndView saveOrder(@ModelAttribute OrderDTO orderDTO) {

        Film film = filmService.getFilm(orderDTO.getFilmId());
        Order order = new Order(film, orderDTO.getCustomerName(), orderDTO.getCustomerPhone());
        Order createdOrder = orderService.createOrder(order);

        ModelAndView model = new ModelAndView("films");
        model.addObject("films", filmService.getAllFilms());
        model.addObject("order", createdOrder);
        return model;
    }


}
