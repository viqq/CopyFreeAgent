package com.prauf.service.impl;


import com.prauf.dao.OrderDao;
import com.prauf.model.Order;
import com.prauf.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("OrderService")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public Order createOrder(Order order) {
        return orderDao.create(order);
    }

    @Transactional
    public Order updateOrder(Order order) {
        return orderDao.update(order);
    }
}
