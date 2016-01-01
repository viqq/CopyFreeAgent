package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.DayDao;
import com.free.agent.model.Day;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 29.12.15.
 */
@Repository
public class DayDaoImpl extends GenericDaoImpl<Day, Long> implements DayDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<Day> getEntityClass() {
        return Day.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
