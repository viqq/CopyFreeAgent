package com.free.agent.service;

import com.free.agent.model.User;

import java.util.Collection;

/**
 * Created by antonPC on 21.06.15.
 */
public interface UserService {
    User save(User user);

    Collection<User> findAll();
}
