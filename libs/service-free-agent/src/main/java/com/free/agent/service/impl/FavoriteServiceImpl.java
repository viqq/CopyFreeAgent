package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.dto.FavoriteDto;
import com.free.agent.exception.UserIsNotFavoriteException;
import com.free.agent.model.User;
import com.free.agent.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by antonPC on 06.12.15.
 */
@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public List<FavoriteDto> findAllByUserEmail(String email) {
        return userDao.findFavoritesByUserId(userDao.findByEmail(email).getId()).stream().map(user -> {
            FavoriteDto dto = new FavoriteDto();
            dto.setUserId(user.getId());
            dto.setUserEmail(user.getEmail());
            return dto;
        }).collect(Collectors.toList());

    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void addUserToFavorite(String email, Long id) {
        User myUser = userDao.findByEmail(email);
        User follower = userDao.find(id);
        myUser.getFavorites().add(follower);
        userDao.update(myUser);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void removeUserFromFavorite(String email, final Long id) throws UserIsNotFavoriteException {
        User myUser = userDao.findByEmail(email);
        Set<User> favorite = myUser.getFavorites();
        User follower = favorite.stream().filter(input -> input.getId().equals(id)).findFirst()
                .orElseThrow(() -> new UserIsNotFavoriteException(String.format("User with id %s is not in favorite set", id)));
        myUser.getFavorites().remove(follower);
        userDao.update(myUser);
    }
}
