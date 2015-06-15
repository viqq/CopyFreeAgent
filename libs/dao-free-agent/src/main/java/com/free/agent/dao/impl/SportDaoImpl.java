package com.free.agent.dao.impl;

import com.free.agent.dao.SportDao;
import com.free.agent.dao.UserDao;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 15.06.15.
 */
@Repository
public class SportDaoImpl  extends GenericDaoImpl<Sport,Long> implements SportDao {

    @PersistenceContext(unitName = "h2")
    protected EntityManager entityManager;

    @Override
    protected Class<Sport> getEntityClass() {
        return Sport.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
