package com.free.agent.service;

import com.free.agent.dto.ScheduleDto;
import com.free.agent.model.Schedule;

import java.util.List;

/**
 * Created by antonPC on 05.12.15.
 */
public interface ScheduleService {

    List<Schedule> findAllByUserId(Long id);

    List<Schedule> findAllByUserEmail(String email);

    void save(String email, ScheduleDto dto);

    void editSchedule(Long id, ScheduleDto dto);

    void delete(String email, Long id);
}
