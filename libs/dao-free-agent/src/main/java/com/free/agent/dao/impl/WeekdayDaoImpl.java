package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.WeekdayDao;
import com.free.agent.model.Weekday;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 09.01.16.
 */
@Repository
public class WeekdayDaoImpl extends GenericDaoImpl<Weekday, Long> implements WeekdayDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<Weekday> getEntityClass() {
        return Weekday.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
