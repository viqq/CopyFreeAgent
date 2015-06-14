package com.free.agent.service;


import com.free.agent.model.Order;

public interface OrderService {

    Order createOrder(Order order);

    Order updateOrder(Order order);
}
