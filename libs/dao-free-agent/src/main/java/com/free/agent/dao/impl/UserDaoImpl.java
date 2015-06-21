package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.model.*;
import com.free.agent.utils.DaoUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Created by antonPC on 15.06.15.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    @Override
    public User findByLogin(String login) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.where(cb.equal(from.get(User_.login), login));
        return DaoUtils.getSingleResult(getEntityManager().createQuery(query).getResultList());
    }
}
