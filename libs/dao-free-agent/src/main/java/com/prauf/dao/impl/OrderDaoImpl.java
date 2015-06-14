package com.prauf.dao.impl;


import com.prauf.dao.OrderDao;

import com.prauf.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order, Integer> implements OrderDao {

    @PersistenceContext(unitName = "h2")
    protected EntityManager entityManager;

    @Override
    protected Class<Order> getEntityClass() {
        return Order.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
