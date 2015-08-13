package com.free.agent.dao.mock;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.impl.MessageDaoImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 29.07.15.
 */
@Repository
public class MessageDaoMock extends MessageDaoImpl {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT_TEST)
    protected EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }
}

