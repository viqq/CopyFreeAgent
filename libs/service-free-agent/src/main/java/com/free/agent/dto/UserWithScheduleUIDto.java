package com.free.agent.dto;

import java.util.List;

/**
 * Created by antonPC on 16.01.16.
 */
public class UserWithScheduleUIDto extends UserWithSportUIDto {
    private List<ScheduleDto> schedules;

    public List<ScheduleDto> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<ScheduleDto> schedules) {
        this.schedules = schedules;
    }
}
