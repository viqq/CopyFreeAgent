package com.free.agent.dao.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.SkillDao;
import com.free.agent.model.Skill;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by antonPC on 12.01.16.
 */
@Repository
public class SkillDaoImpl extends GenericDaoImpl<Skill, Long> implements SkillDao {

    @PersistenceContext(unitName = FreeAgentConstant.PERSISTENCE_CONTEXT)
    protected EntityManager entityManager;

    @Override
    protected Class<Skill> getEntityClass() {
        return Skill.class;
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }

}
