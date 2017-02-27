package com.free.agent.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by antonPC on 16.01.16.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserWithScheduleUIDto extends UserWithSportUIDto {

    private List<ScheduleDto> schedules;

}
