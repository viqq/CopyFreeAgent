package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.dto.FavoriteDto;
import com.free.agent.exception.UserIsNotFavoriteException;
import com.free.agent.model.User;
import com.free.agent.service.FavoriteService;
import com.free.agent.util.ExtractFunction;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Set;

/**
 * Created by antonPC on 06.12.15.
 */
@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {
    private static final Logger LOGGER = Logger.getLogger(FavoriteServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Collection<FavoriteDto> findAllByUserEmail(String email) {
        return Collections2.transform(userDao.findFavoritesByUserId(userDao.findByEmail(email).getId()),
                ExtractFunction.FAVORITE_INVOKE);
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
        Optional<User> follower = FluentIterable.from(favorite).filter(new Predicate<User>() {
            @Override
            public boolean apply(User input) {
                return input.getId().equals(id);
            }
        }).first();
        if (!follower.isPresent()) {
            LOGGER.error("User with id " + id + " is not in favorite set");
            throw new UserIsNotFavoriteException("User with id " + id + " is not in favorite set");
        }
        myUser.getFavorites().remove(follower.get());
        userDao.update(myUser);

    }
}
