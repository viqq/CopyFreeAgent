package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.DayDao;
import com.free.agent.dao.ScheduleDao;
import com.free.agent.dao.SportDao;
import com.free.agent.dao.UserDao;
import com.free.agent.dto.ScheduleDto;
import com.free.agent.model.Day;
import com.free.agent.model.Schedule;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.free.agent.service.ScheduleService;
import com.free.agent.util.ExtractFunction;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 05.12.15.
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
    private static final Logger LOGGER = Logger.getLogger(ScheduleServiceImpl.class);

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SportDao sportDao;

    @Autowired
    private DayDao dayDao;

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public List<Schedule> findAllByUserId(Long id) {
        return scheduleDao.findAllByUserId(id);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public List<Schedule> findAllByUserEmail(String email) {
        return scheduleDao.findAllByUserEmail(email);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void save(String email, ScheduleDto scheduleDto) {
        User user = userDao.findByEmail(email);
        Schedule schedule = ExtractFunction.getSchedule(scheduleDto);
        Set<Day> days = dayDao.saveAll(scheduleDto.getDays());
        schedule.setDays(days);
        Sport sport = sportDao.findByName(scheduleDto.getSport());
        if (!user.getSports().contains(sport)) {
            //todo
        }
        schedule.setSport(sportDao.findByName(scheduleDto.getSport()));
        schedule.setUser(user);
        user.getSchedules().add(schedule);
        userDao.update(user);
        scheduleDao.update(schedule);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void editSchedule(Long id, ScheduleDto dto) {
        //todo
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void delete(String email, final Long id) {
        User user = userDao.findByEmail(email);
        List<Schedule> schedules = scheduleDao.findAllByUserId(user.getId());
        Optional<Schedule> scheduleOptional = FluentIterable.from(schedules).firstMatch(new Predicate<Schedule>() {
            @Override
            public boolean apply(Schedule input) {
                return input.getId().equals(id);
            }
        });
        if (!scheduleOptional.isPresent()) {
            //todo
        }
        Schedule schedule = scheduleOptional.get();
        user.getSchedules().remove(schedule);
        schedule.setSport(null);
        schedule.setUser(null);
        userDao.update(user);
        scheduleDao.delete(schedule);
    }
}
