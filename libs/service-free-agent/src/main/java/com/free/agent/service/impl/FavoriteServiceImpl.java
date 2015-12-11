package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.FavoriteDao;
import com.free.agent.dao.UserDao;
import com.free.agent.model.Favorite;
import com.free.agent.model.User;
import com.free.agent.service.FavoriteService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by antonPC on 06.12.15.
 */
@Service("favoriteService")
public class FavoriteServiceImpl implements FavoriteService {
    private static final Logger LOGGER = Logger.getLogger(FavoriteServiceImpl.class);

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public List<Favorite> findAllByUserEmail(String email) {
        return favoriteDao.findAllByUserId(userDao.findByEmail(email).getId());
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void addUserToFavorite(String email, Long id) {
        User myUser = userDao.findByEmail(email);
        User favoriteUser = userDao.find(id);
        Favorite favorite = new Favorite(myUser, favoriteUser, "comment");
        myUser.getFavorites().add(favorite);
        favoriteDao.create(favorite);
        userDao.update(myUser);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void removeUserFromFavorite(String email, Long id) {
        User myUser = userDao.findByEmail(email);
        Favorite favorite = favoriteDao.findAllByUserAndFollower(myUser.getId(), id);
        myUser.getFavorites().remove(favorite);
        userDao.update(myUser);
        favoriteDao.delete(favorite);
    }
}
