package com.free.agent.dao;

import com.free.agent.Filter;
import com.free.agent.model.User;

import java.util.Collection;


/**
 * Created by antonPC on 15.06.15.
 */
public interface UserDao extends GenericDao<User,Long> {

    User findByEmail(String email);

    Collection<User> findByFilter(Filter filter);

    User findByHash(String hash);

}
