package com.free.agent.service;

import com.free.agent.model.User;

import java.util.Collection;
import java.util.Set;

/**
 * Created by antonPC on 21.06.15.
 */
public interface UserService {
    User save(User user,Set<String> names);

    Collection<User> findAll();

    User findByLogin(String login);
}
