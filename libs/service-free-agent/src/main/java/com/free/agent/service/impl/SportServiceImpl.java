package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.SportDao;
import com.free.agent.dto.SportUIDto;
import com.free.agent.model.Sport;
import com.free.agent.service.SportService;
import com.free.agent.util.ExtractFunction;
import com.google.common.collect.Collections2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by antonPC on 25.06.15.
 */
@Service("sportService")
public class SportServiceImpl implements SportService {
    private static final Logger LOGGER = Logger.getLogger(SportServiceImpl.class);

    @Autowired
    private SportDao sportDao;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public Sport save(Sport sport) {
        Sport savedSport = sportDao.create(sport);
        LOGGER.info("New sport " + sport.getName() + " was added");
        return savedSport;
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public Collection<SportUIDto> getAllSports() {
        return Collections2.transform(sportDao.findAll(), ExtractFunction.SPORT_NAME_INVOKE);
    }
}
