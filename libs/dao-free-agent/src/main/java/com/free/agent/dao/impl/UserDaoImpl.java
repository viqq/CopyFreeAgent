package com.free.agent.dao.impl;

import com.free.agent.Filter;
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
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.Collection;

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
    public void updateUser(User editedUser) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<User> updateCriteria = cb.createCriteriaUpdate(User.class);
        Root<User> from = updateCriteria.from(User.class);
        updateCriteria.set(from.get(User_.firstName), editedUser.getFirstName())
                .set(from.get(User_.lastName), editedUser.getLastName())
                        //todo
                        //.set(from.get(User_.sports), editedUser.getSports())
                .set(from.get(User_.city), editedUser.getCity())
                .set(from.get(User_.description), editedUser.getDescription())
                .set(from.get(User_.dateOfBirth), editedUser.getDateOfBirth())
                .set(from.get(User_.phone), editedUser.getPhone())
                .set(from.get(User_.gender), editedUser.getGender());

        updateCriteria.where(cb.equal(from.get(User_.id), editedUser.getId()));
        getEntityManager().createQuery(updateCriteria).executeUpdate();
    }
}
