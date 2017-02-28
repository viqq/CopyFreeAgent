package com.free.agent.converter;

import com.free.agent.dto.ScheduleDto;
import com.free.agent.model.Schedule;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created by antonPC on 28.02.17.
 */
@Component
public class Schedule2ScheduleDto implements Converter<Schedule, ScheduleDto> {

    @Override
    public ScheduleDto convert(Schedule input) {
        ScheduleDto dto = new ScheduleDto();
        dto.setId(input.getId());
        dto.setSportId(input.getSport().getId());
        dto.setStartTime(input.getStartTime().getTime());
        dto.setEndTime(input.getEndTime().getTime());
        dto.setDayOfWeeks(input.getWeekdays().stream().map(weekday -> weekday.getDayOfWeek().name()).collect(Collectors.toSet()));
        dto.setDays(input.getDays().stream().map(day -> day.getDate().getTime()).collect(Collectors.toSet()));
        return dto;
    }
}
