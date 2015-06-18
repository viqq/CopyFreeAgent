package com.free.agent.dao.impl;

import com.free.agent.dao.GenericDao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDaoImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

    protected abstract Class<T> getEntityClass();

    //@PersistenceContext(unitName = "h2")
    //protected EntityManager entityManager;

    protected abstract EntityManager getEntityManager();

    public T create(T t) {
        getEntityManager().persist(t);
        return t;
    }

    public T read(PK id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    public T update(T t) {
        return getEntityManager().merge(t);
    }

    public void delete(T t) {
        getEntityManager().remove(t);
    }

    public List<T> getAll() {
        Query query = getEntityManager().createQuery("from " + getEntityClass().getName());
        return query.getResultList();
    }

    public void removeAll(){
        for (T t : getAll()){
            delete(t);
        }
    }
}
