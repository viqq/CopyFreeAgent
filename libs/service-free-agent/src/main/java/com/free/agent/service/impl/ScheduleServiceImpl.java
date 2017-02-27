package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.*;
import com.free.agent.dto.ScheduleDto;
import com.free.agent.exception.ScheduleNotFoundException;
import com.free.agent.exception.SportNotSupportedException;
import com.free.agent.field.DayOfWeek;
import com.free.agent.model.*;
import com.free.agent.service.ScheduleService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.free.agent.util.FunctionUtils.getSchedule;

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

    @Autowired
    private WeekdayDao weekdayDao;

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
        //todo cascade type
        User user = userDao.findByEmail(email);
        Schedule schedule = getSchedule(scheduleDto);
        Set<Day> days = scheduleDto.getDays().stream().map(input -> {
            DateTime dateTime = new DateTime((long) input);
            String dayOfWeek = new DateTime().dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase();
            Day day = new Day();
            day.setDate(dateTime.toDate());
            day.setDayOfWeek(DayOfWeek.valueOf(dayOfWeek));
            return day;
        }).collect(Collectors.toSet());
        for (Day day : days) {
            day.setSchedule(schedule);
        }
        // dayDao.saveAll(days);
        schedule.setDays(days);
        Set<Weekday> weekdays = scheduleDto.getDayOfWeeks().stream().map(input -> {
            Weekday weekday = new Weekday();
            weekday.setDayOfWeek(DayOfWeek.valueOf(input));
            return weekday;
        }).collect(Collectors.toSet());
        for (Weekday weekday : weekdays) {
            weekday.setSchedule(schedule);
        }
        // weekdayDao.saveAll(weekdays);
        schedule.setWeekdays(weekdays);
        Sport sport = sportDao.find(scheduleDto.getSportId());
        if (!user.getSports().contains(sport)) {
            LOGGER.error(String.format("You can not select sport %s for your schedule",sport));
            throw new SportNotSupportedException(String.format("You can not select sport %s for your schedule",sport));
        }
        schedule.setSport(sport);
        schedule.setUser(user);
        user.getSchedules().add(schedule);
        userDao.update(user);
        //scheduleDao.update(schedule);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void editSchedule(Long id, ScheduleDto dto) {
        Schedule schedule = scheduleDao.find(id);
        schedule.setSport(sportDao.find(dto.getSportId()));
        schedule.setStartTime(new DateTime(dto.getStartTime()).toDate());
        schedule.setEndTime(new DateTime(dto.getEndTime()).toDate());
        schedule.setDays(dto.getDays().stream().map(input -> {
            DateTime dateTime = new DateTime((long) input);
            String dayOfWeek = new DateTime().dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase();
            Day day = new Day();
            day.setDate(dateTime.toDate());
            day.setDayOfWeek(DayOfWeek.valueOf(dayOfWeek));
            return day;
        }).collect(Collectors.toSet()));
        schedule.setWeekdays(dto.getDayOfWeeks().stream().map(input -> {
            Weekday weekday = new Weekday();
            weekday.setDayOfWeek(DayOfWeek.valueOf(input));
            return weekday;
        }).collect(Collectors.toSet()));
        scheduleDao.update(schedule);
    }

    @Override
    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public void delete(String email, final Long id) {
        User user = userDao.findByEmail(email);
        List<Schedule> schedules = scheduleDao.findAllByUserId(user.getId());
        Optional<Schedule> scheduleOptional = schedules.stream().filter(input -> input.getId().equals(id)).findFirst();
        if (!scheduleOptional.isPresent()) {
            throw new ScheduleNotFoundException(String.format("Schedule with id %s is not found", id));
        }
        Schedule schedule = scheduleOptional.get();
        user.getSchedules().remove(schedule);
        schedule.setSport(null);
        schedule.setUser(null);
        userDao.update(user);
        scheduleDao.delete(schedule);
    }

}
