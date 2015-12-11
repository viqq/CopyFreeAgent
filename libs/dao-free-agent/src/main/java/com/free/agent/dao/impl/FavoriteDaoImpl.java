package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.FavoriteDao;
import com.free.agent.model.Favorite;
import com.free.agent.model.Favorite_;
import com.free.agent.model.User;
import com.free.agent.model.User_;
import com.free.agent.utils.DaoUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by antonPC on 06.12.15.
 */
@Repository
public class FavoriteDaoImpl extends GenericDaoImpl<Favorite, Long> implements FavoriteDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<Favorite> getEntityClass() {
        return Favorite.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    @Override
    public List<Favorite> findAllByUserId(Long userId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Favorite> query = cb.createQuery(Favorite.class);
        Root<Favorite> fromFavorite = query.from(Favorite.class);
        Join<Favorite, User> fromUser = fromFavorite.join(Favorite_.user);
        query.where(cb.equal(fromUser.get(User_.id), userId));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public Favorite findAllByUserAndFollower(Long myId, Long followerId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Favorite> query = cb.createQuery(Favorite.class);
        Root<Favorite> fromFavorite = query.from(Favorite.class);
        Join<Favorite, User> fromUser = fromFavorite.join(Favorite_.user);
        Join<Favorite, User> fromFollower = fromFavorite.join(Favorite_.favoriteUser);
        query.where(cb.equal(fromUser.get(User_.id), myId), cb.equal(fromFollower.get(User_.id), followerId));
        return DaoUtils.getSingleResult(getEntityManager().createQuery(query).getResultList());
    }
}
