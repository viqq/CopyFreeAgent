package com.free.agent.service;

import com.free.agent.dto.FavoriteDto;
import com.free.agent.exception.UserIsNotFavoriteException;

import java.util.List;


/**
 * Created by antonPC on 06.12.15.
 */
public interface FavoriteService {

    List<FavoriteDto> findAllByUserEmail(String email);

    void addUserToFavorite(String name, Long id);

    void removeUserFromFavorite(String email, Long id) throws UserIsNotFavoriteException;
}
