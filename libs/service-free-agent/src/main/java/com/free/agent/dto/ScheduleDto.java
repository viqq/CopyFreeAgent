package com.free.agent.dto;

import com.free.agent.field.DayOfWeek;

import java.util.Date;
import java.util.Set;

/**
 * Created by antonPC on 01.01.16.
 */
public class ScheduleDto {
    private Long id;
    private Set<DayOfWeek> dayOfWeeks; //todo String?
    private Set<Long> days;
    private String sport;
    private Date startTime;
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<DayOfWeek> getDayOfWeeks() {
        return dayOfWeeks;
    }

    public void setDayOfWeeks(Set<DayOfWeek> dayOfWeeks) {
        this.dayOfWeeks = dayOfWeeks;
    }

    public Set<Long> getDays() {
        return days;
    }

    public void setDays(Set<Long> days) {
        this.days = days;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
