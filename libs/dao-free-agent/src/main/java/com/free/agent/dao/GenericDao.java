package com.free.agent.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GenericDao<T, PK extends Serializable> {
    T create(T t);

    T find(PK id);

    T update(T t);

    void delete(T t);

    List<T> findAll();

    void deleteAll();

    void delete(Collection<T> ts);

    Set<T> saveAll(Collection<T> ts);
}