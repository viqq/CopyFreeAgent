package com.free.agent.converter;

import com.free.agent.dto.ScheduleDto;
import com.free.agent.model.Schedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.sql.Time;

/**
 * Created by antonPC on 28.02.17.
 */
@Component
public class ScheduleDto2Schedule implements Converter<ScheduleDto, Schedule> {

    @Override
    public Schedule convert(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setStartTime(new Time(scheduleDto.getStartTime()));
        schedule.setEndTime(new Time(scheduleDto.getEndTime()));
        return schedule;
    }
}
