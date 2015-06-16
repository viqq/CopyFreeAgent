package com.free.agent.dao;

import com.free.agent.model.User;

/**
 * Created by antonPC on 15.06.15.
 */
public interface UserDao extends GenericDao<User,Long> {

    User findByLogin(String login);
}
