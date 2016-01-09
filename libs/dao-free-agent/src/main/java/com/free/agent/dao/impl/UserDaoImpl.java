package com.free.agent.dao.impl;

import com.free.agent.Filter;
import com.free.agent.FilterNew;
import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.model.User;
import com.free.agent.model.User_;
import com.free.agent.utils.DaoUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.SetJoin;
import java.util.Collection;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Repository
public class UserDaoImpl extends GenericDaoImpl<User, Long> implements UserDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }


    @Override
    public User findByEmail(String email) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.where(cb.equal(from.get(User_.email), email));
        return DaoUtils.getSingleResult(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    public Collection<User> findByFilter(Filter filter) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        query.where(filter.getPredicate(cb, query));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public Collection<User> findByFilter(FilterNew filter) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        query.where(filter.getPredicate(cb, query));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public User findByHash(String hash) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> from = query.from(User.class);
        query.where(cb.equal(from.get(User_.hash), hash));
        return DaoUtils.getSingleResult(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    public Set<User> findFavoritesByUserId(Long userId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> fromUser = query.from(User.class);
        SetJoin<User, User> fromFavorites = fromUser.join(User_.favorites);
        query.where(cb.equal(fromUser.get(User_.id), userId));
        query.select(fromFavorites);
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }
}
