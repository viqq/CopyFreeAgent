package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.SportDao;
import com.free.agent.model.Sport;
import com.free.agent.model.Sport_;
import com.free.agent.utils.DaoUtils;
import com.free.agent.utils.PredicateBuilder;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Repository
public class SportDaoImpl extends GenericDaoImpl<Sport, Long> implements SportDao {
    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<Sport> getEntityClass() {
        return Sport.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    @Cacheable(value = "sportCache")
    public Set<Sport> findByNames(Set<String> name) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Sport> query = cb.createQuery(Sport.class);
        Root<Sport> from = query.from(Sport.class);
        query.where(new PredicateBuilder(cb).addInPredicate(from.get(Sport_.name), name).buildWithAndConjunction());
        return DaoUtils.getResultSet(getEntityManager().createQuery(query).getResultList());
    }

    @Override
    @Cacheable(value = "sportCache")
    public List<Sport> findAll() {
        return super.findAll();
    }

    @Override
    @CachePut(value = "sportCache")
    public Sport create(Sport sport) {
        return super.create(sport);
    }

    @Override
    @CacheEvict(value = "sportCache", allEntries = true)
    public Sport update(Sport sport) {
        return super.update(sport);
    }

}



