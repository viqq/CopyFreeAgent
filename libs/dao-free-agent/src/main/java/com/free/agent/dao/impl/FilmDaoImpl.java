package com.free.agent.dao.impl;


import com.free.agent.dao.FilmDao;

import com.free.agent.model.Film;
import com.free.agent.model.Film_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class FilmDaoImpl extends GenericDaoImpl<Film,Integer> implements FilmDao {


    @PersistenceContext(unitName = "h2")
    protected EntityManager entityManager;



    @Override
    protected Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        Film_ f;
        return entityManager;
    }

}
