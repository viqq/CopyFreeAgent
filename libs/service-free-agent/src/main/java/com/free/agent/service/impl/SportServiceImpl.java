package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.SportDao;
import com.free.agent.dto.SportUIDto;
import com.free.agent.model.Sport;
import com.free.agent.service.SportService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antonPC on 25.06.15.
 */
@Service("sportService")
public class SportServiceImpl implements SportService {
    private static final Logger LOGGER = Logger.getLogger(SportServiceImpl.class);

    @Autowired
    private SportDao sportDao;

    @Autowired
    ConversionService conversionService;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public Sport save(Sport sport) {
        Sport savedSport = sportDao.create(sport);
        LOGGER.info(String.format("New sport %s was added", sport.getNameEn()));
        return savedSport;
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public List<SportUIDto> getAllSports() {
        return sportDao.findAll().stream().map(sport -> conversionService.convert(sport, SportUIDto.class))
                .collect(Collectors.toList());
    }
}
