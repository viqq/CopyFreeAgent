package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.MessageDao;
import com.free.agent.model.Message;
import com.free.agent.model.Message_;
import com.free.agent.model.User;
import com.free.agent.model.User_;
import com.free.agent.utils.DaoUtils;
import com.free.agent.utils.PredicateBuilder;
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
    public Set<Message> findAllByReceiver(String login) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> fromMessage = query.from(Message.class);
        Join<Message, User> fromUser = fromMessage.join(Message_.user);
        query.where(cb.equal(fromUser.get(User_.login), login));
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    public Set<Message> findAllByReceiverAndAuthor(String login, String author) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Message> query = cb.createQuery(Message.class);
        Root<Message> fromMessage = query.from(Message.class);
        Join<Message, User> fromUser = fromMessage.join(Message_.user);
        query.where(cb.equal(fromUser.get(User_.login), login),
                cb.equal(fromMessage.get(Message_.author), author));
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
    public int countUnreadMessages(String login) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Message> fromMessage = query.from(Message.class);
        Join<Message, User> fromUser = fromMessage.join(Message_.user);
        query.where(cb.equal(fromUser.get(User_.login), login),
                cb.isNull(fromMessage.get(Message_.timeOfRead)));
        Expression<Long> count = cb.count(fromMessage);
        query.select(count);
        return getEntityManager().createQuery(query).getSingleResult().intValue();
    }


}
