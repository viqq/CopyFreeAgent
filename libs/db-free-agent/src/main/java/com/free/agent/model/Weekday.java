package com.free.agent.model;

import com.free.agent.field.DayOfWeek;

import javax.persistence.*;

/**
 * Created by antonPC on 09.01.16.
 */
@Entity
@Table(name = "WEEKDAY")
public class Weekday extends AbstractTable<Long> {

    @Id
    @Column(name = "WEEKDAY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "DAY_OF_WEEK")
    private DayOfWeek dayOfWeek;

    public Weekday(DayOfWeek dayOfWeek, Schedule schedule) {
        this.dayOfWeek = dayOfWeek;
        this.schedule = schedule;
    }

    public Weekday(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Weekday() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
