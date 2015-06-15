package com.free.agent.dao.mock;

import com.free.agent.dao.impl.UserDaoImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 15.06.15.
 */
@Repository
public class UserDaoUtil extends UserDaoImpl {
    @PersistenceContext(unitName = "h2Test")
    protected EntityManager entityManager;





    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}
