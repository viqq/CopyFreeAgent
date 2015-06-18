package com.free.agent.dao.impl;

import com.free.agent.dao.GenericDao;
import com.free.agent.model.User;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    private final static String FROM = "FROM ";
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

    public List<T> getAll() {
        Query query = getEntityManager().createQuery(FROM + getEntityClass().getName());
        return query.getResultList();
    }

    public void removeAll(){
        for (T t : getAll()){
            delete(t);
        }
    }
}
