package com.prauf.dao;

import com.prauf.dao.impl.FilmDaoImpl;
import com.prauf.dao.impl.GenericDaoImpl;
import com.prauf.model.Film;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 13.06.15.
 */
@Repository
public class FilmDaoUtil extends FilmDaoImpl {


    @PersistenceContext(unitName = "h2Test")
    protected EntityManager entityManager;





    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
