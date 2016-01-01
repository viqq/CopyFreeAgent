package com.free.agent.dao;

import com.free.agent.model.Schedule;

import java.util.List;

/**
 * Created by antonPC on 05.12.15.
 */
public interface ScheduleDao extends GenericDao<Schedule, Long> {

    List<Schedule> findAllByUserId(Long id);

    List<Schedule> findAllByUserEmail(String email);
}
