package com.free.agent.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
    T create(T t);

    T find(PK id);

    T update(T t);

    void delete(T t);

    List<T> findAll();

    void removeAll();
}