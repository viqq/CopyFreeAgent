package com.free.agent.dto;

import java.util.Set;

/**
 * Created by antonPC on 01.01.16.
 */
public class ScheduleDto {
    private Long id;
    private Set<String> dayOfWeeks;
    private Set<Long> days;
    private Long sportId;
    private Long startTime;
    private Long endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<String> getDayOfWeeks() {
        return dayOfWeeks;
    }

    public void setDayOfWeeks(Set<String> dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }

    public Set<Long> getDays() {
        return days;
    }

    public void setDays(Set<Long> days) {
        this.days = days;
    }

    public Long getSportId() {
        return sportId;
    }

    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
