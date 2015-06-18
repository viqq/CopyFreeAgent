package com.free.agent.dao.impl;


import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.OrderDao;

import com.free.agent.model.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class OrderDaoImpl extends GenericDaoImpl<Order, Integer> implements OrderDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
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
