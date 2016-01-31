package com.free.agent.dao;

import com.free.agent.field.Language;
import com.free.agent.model.Sport;

import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
public interface SportDao extends GenericDao<Sport, Long> {

    Set<Sport> findByNames(Set<String> name, Language language);

    Sport findByName(String sport, Language language);

    Set<Sport> findByIds(Set<Long> sportIds);
}
