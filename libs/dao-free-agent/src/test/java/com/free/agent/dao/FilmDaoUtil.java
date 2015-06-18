package com.free.agent.dao;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.impl.FilmDaoImpl;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 13.06.15.
 */
@Repository
public class FilmDaoUtil extends FilmDaoImpl {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT_TEST)
    protected EntityManager entityManager;

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
