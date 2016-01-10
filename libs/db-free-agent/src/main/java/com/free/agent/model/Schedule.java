package com.free.agent.model;

import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by antonPC on 05.12.15.
 */
@Entity
@Table(name = "SCHEDULE")
public class Schedule extends AbstractTable<Long> {

    @Id
    @Column(name = "SCHEDULE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SPORT_ID")
    private Sport sport;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "schedule")
    private Set<Weekday> weekdays;

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "schedule")
    private Set<Day> days;

    @Column(name = "START_TIME")
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Column(name = "END_TIME")
    @Temporal(TemporalType.TIME)
    private Date endTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Set<Weekday> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(Set<Weekday> weekdays) {
        this.weekdays = weekdays;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> days) {
        this.days = days;
    }

    public Date getStartTime() {
        return ObjectUtils.clone(startTime);
    }

    public void setStartTime(Date startTime) {
        this.startTime = ObjectUtils.clone(startTime);
    }

    public Date getEndTime() {
        return ObjectUtils.clone(endTime);
    }

    public void setEndTime(Date endTime) {
        this.endTime = ObjectUtils.clone(endTime);
    }
}
