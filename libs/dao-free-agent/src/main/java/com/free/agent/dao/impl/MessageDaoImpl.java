package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.MessageDao;
import com.free.agent.model.Message;
import com.free.agent.model.Message_;
import com.free.agent.model.User;
import com.free.agent.model.User_;
import com.free.agent.utils.DaoUtils;
import com.free.agent.utils.PredicateBuilder;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by antonPC on 29.07.15.
 */
@Repository
public class MessageDaoImpl extends GenericDaoImpl<Message, Long> implements MessageDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<Message> getEntityClass() {
        return Message.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public Set<Message> findAllByReceiver(String email) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> fromMessage = query.from(Message.class);
        Join<Message, User> fromUser = fromMessage.join(Message_.user);
        query.where(cb.equal(fromUser.get(User_.email), email));
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    public Set<Message> findAllByReceiverAndAuthor(Long id, Long authorId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> fromMessage = query.from(Message.class);
        Join<Message, User> fromUser = fromMessage.join(Message_.user);
        Predicate predicate = new PredicateBuilder(cb)
                .addEqualsPredicate(cb, fromUser.get(User_.id), id)
                .addEqualsPredicate(fromMessage.get(Message_.authorId), authorId)
                .buildWithAndConjunction();
        query.where(predicate);
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }


    @Override
    public Set<Message> findOlderThen(Date date) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> from = query.from(Message.class);
        query.where(new PredicateBuilder(cb).addRangePredicate(from.get(Message_.timeOfCreate), null, date).buildWithAndConjunction());
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    public int countUnreadMessages(String email) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Message> fromMessage = query.from(Message.class);
        Join<Message, User> fromUser = fromMessage.join(Message_.user);
        query.where(cb.equal(fromUser.get(User_.email), email),
                cb.isNull(fromMessage.get(Message_.timeOfRead)));
        Expression<Long> count = cb.count(fromMessage);
        query.select(count);
        return getEntityManager().createQuery(query).getSingleResult().intValue();
    }

    @Override
    public Set<Message> findAllByAuthorEmailAndId(Long authorId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> fromMessage = query.from(Message.class);
        Predicate predicate = new PredicateBuilder(cb)
                .addEqualsPredicate(fromMessage.get(Message_.authorId), authorId)
                .buildWithAndConjunction();
        query.where(predicate);
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    public Set<Message> findAllByAuthorId(Long authorId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> fromMessage = query.from(Message.class);
        query.where(cb.equal(fromMessage.get(Message_.authorId), authorId));
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    public Set<Message> getHistory(Long userId, Long authorId) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> fromMessage = query.from(Message.class);
        Join<Message, User> fromUser = fromMessage.join(Message_.user);

        Predicate p1, p3 = null;
        p1 = new PredicateBuilder(cb).addEqualsPredicate(fromUser.get(User_.id), userId)
                .addEqualsPredicate(fromMessage.get(Message_.authorId), authorId)
                .buildWithAndConjunction();

        if (authorId != null) {
            p3 = new PredicateBuilder(cb).addEqualsPredicate(fromUser.get(User_.id), authorId)
                    .addEqualsPredicate(fromMessage.get(Message_.authorId), userId)
                    .buildWithAndConjunction();
        }

        query.where(cb.or(cb.and(p1), cb.and(p3)));
        query.orderBy(cb.asc(fromMessage.get(Message_.timeOfCreate)));
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    public Set<Long> getParticipants(Long id) {
        Set<Long> result = Sets.newHashSet();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Message> fromMessage = query.from(Message.class);
        Join<Message, User> fromUser = fromMessage.join(Message_.user);
        query.distinct(true);

        query.select(fromMessage.get(Message_.authorId));
        query.where(cb.equal(fromUser.get(User_.id), id));
        result.addAll(getEntityManager().createQuery(query).getResultList());

        query.select(fromUser.get(User_.id));
        query.where(cb.equal(fromMessage.get(Message_.authorId), id));
        result.addAll(getEntityManager().createQuery(query).getResultList());

        return result;
    }
}
