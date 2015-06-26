package com.free.agent.service;

import com.free.agent.model.Sport;

import java.util.Collection;

/**
 * Created by antonPC on 25.06.15.
 */
public interface SportService {

    Sport save(Sport sport);

    Collection<Sport> getAllSports();
}
