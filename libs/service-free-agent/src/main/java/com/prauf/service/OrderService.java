package com.prauf.service;


import com.prauf.model.Order;

public interface OrderService {

    Order createOrder(Order order);

    Order updateOrder(Order order);
}
