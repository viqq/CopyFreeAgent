package com.free.agent.model;

import com.free.agent.field.Weekday;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by antonPC on 29.12.15.
 */
@Entity
@Table(name = "DAY")
public class Day extends AbstractTable<Long> {

    @Id
    @Column(name = "DAY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "WEEKDAY")
    private Weekday weekday;

    public Day() {
    }

    public Day(Date date, Weekday weekday) {
        this.date = date;
        this.weekday = weekday;
    }

    public Day(Schedule schedule, Date date, Weekday weekday) {
        this.schedule = schedule;
        this.date = date;
        this.weekday = weekday;
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

    public Date getDate() {
        return ObjectUtils.clone(date);
    }

    public void setDate(Date date) {
        this.date = ObjectUtils.clone(date);
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }
}
