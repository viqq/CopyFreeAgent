package com.free.agent.dao.impl;

import com.free.agent.dao.GenericDao;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected abstract Class<T> getEntityClass();

    protected abstract EntityManager getEntityManager();

    public T create(T t) {
        getEntityManager().persist(t);
        return t;
    }

    public T find(PK id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public T update(T t) {
        return getEntityManager().merge(t);
    }

    public void delete(T t) {
        getEntityManager().remove(t);
    }

    public List<T> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(getEntityClass());
        criteria.from(getEntityClass());
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public void deleteAll() {
        for (T t : findAll()){
            delete(t);
        }
    }

    public void delete(Collection<T> entries) {
        for (T t : entries) {
            delete(t);
        }
    }
}
