package com.free.agent.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by antonPC on 16.01.16.
 */
@Data
public class UserWithScheduleUIDto extends UserWithSportUIDto {
    private List<ScheduleDto> schedules;

}
