package com.free.agent.service;

import com.free.agent.dto.SportUIDto;
import com.free.agent.model.Sport;

import java.util.List;

/**
 * Created by antonPC on 25.06.15.
 */
public interface SportService {

    Sport save(Sport sport);

    List<SportUIDto> getAllSports();
}
