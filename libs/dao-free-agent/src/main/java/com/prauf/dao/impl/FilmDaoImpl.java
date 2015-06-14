package com.prauf.dao.impl;


import com.prauf.dao.FilmDao;

import com.prauf.model.Film;
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
        return entityManager;
    }

}
