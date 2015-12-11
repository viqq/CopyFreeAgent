package com.free.agent.dao;

import com.free.agent.model.Favorite;

import java.util.List;


/**
 * Created by antonPC on 06.12.15.
 */
public interface FavoriteDao extends GenericDao<Favorite, Long> {

    List<Favorite> findAllByUserId(Long userId);

    Favorite findAllByUserAndFollower(Long myId, Long followerId);
}
