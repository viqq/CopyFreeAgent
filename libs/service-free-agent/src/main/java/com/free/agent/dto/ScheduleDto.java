package com.free.agent.dto;

import lombok.Data;

import java.util.Set;

/**
 * Created by antonPC on 01.01.16.
 */
@Data
public class ScheduleDto {

    private Long id;
    private Set<String> dayOfWeeks;
    private Set<Long> days;
    private Long sportId;
    private Long startTime;
    private Long endTime;

}
