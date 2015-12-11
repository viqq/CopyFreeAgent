package com.free.agent.service;

import com.free.agent.model.Favorite;

import java.util.List;

/**
 * Created by antonPC on 06.12.15.
 */
public interface FavoriteService {

    List<Favorite> findAllByUserEmail(String email);

    void addUserToFavorite(String name, Long id);

    void removeUserFromFavorite(String email, Long id);
}
