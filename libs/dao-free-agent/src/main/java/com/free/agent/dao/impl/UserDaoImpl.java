package com.free.agent.dao.impl;

import com.free.agent.dao.FilmDao;
import com.free.agent.dao.UserDao;
import com.free.agent.model.Film;
import com.free.agent.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 15.06.15.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User,Long> implements UserDao {

    @PersistenceContext(unitName = "h2")
    protected EntityManager entityManager;

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
