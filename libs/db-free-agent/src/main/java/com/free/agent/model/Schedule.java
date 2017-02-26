package com.free.agent.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by antonPC on 05.12.15.
 */
@Getter
@Setter
@NoArgsConstructor
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

}
