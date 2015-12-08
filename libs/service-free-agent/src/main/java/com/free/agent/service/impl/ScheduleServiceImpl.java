package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.ScheduleDao;
import com.free.agent.model.Schedule;
import com.free.agent.service.ScheduleService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by antonPC on 05.12.15.
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger LOGGER = Logger.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public List<Schedule> findAllByUserId(Long id) {
        return scheduleDao.findAllByUserId(id);
    }
}
