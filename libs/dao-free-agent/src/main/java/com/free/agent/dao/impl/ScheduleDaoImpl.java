package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.ScheduleDao;
import com.free.agent.model.Schedule;
import com.free.agent.model.Schedule_;
import com.free.agent.model.User;
import com.free.agent.model.User_;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by antonPC on 05.12.15.
 */
@Repository
public class ScheduleDaoImpl extends GenericDaoImpl<Schedule, Long> implements ScheduleDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<Schedule> getEntityClass() {
        return Schedule.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public List<Schedule> findAllByUserId(Long id) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Schedule> query = cb.createQuery(Schedule.class);
        Root<Schedule> fromSchedule = query.from(Schedule.class);
        Join<Schedule, User> fromUser = fromSchedule.join(Schedule_.user);
        query.where(cb.equal(fromUser.get(User_.id), id));
        return getEntityManager().createQuery(query).getResultList();
    }

    @Override
    public List<Schedule> findAllByUserEmail(String email) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Schedule> query = cb.createQuery(Schedule.class);
        Root<Schedule> fromSchedule = query.from(Schedule.class);
        Join<Schedule, User> fromUser = fromSchedule.join(Schedule_.user);
        query.where(cb.equal(fromUser.get(User_.email), email));
        return getEntityManager().createQuery(query).getResultList();
    }
}
