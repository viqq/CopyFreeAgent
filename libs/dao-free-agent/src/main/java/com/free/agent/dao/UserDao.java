package com.free.agent.dao;

import com.free.agent.Filter;
import com.free.agent.FilterNew;
import com.free.agent.model.User;

import java.util.List;
import java.util.Set;


/**
 * Created by antonPC on 15.06.15.
 */
public interface UserDao extends GenericDao<User,Long> {

    User findByEmail(String email);

    @Deprecated
    List<User> findByFilter(Filter filter);

    List<User> findByFilter(FilterNew filter);

    List<User> findByNotFilter(FilterNew filter, String city, String country, Integer startIndex);

    User findByHash(String hash);

    Set<User> findFavoritesByUserId(Long userId);


}
