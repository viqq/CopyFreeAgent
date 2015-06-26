package com.free.agent.dao;

import com.free.agent.model.Sport;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
public interface SportDao extends GenericDao<Sport,Long>{

    Set<Sport> findByNames(Set<String> name);
}
