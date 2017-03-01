package com.free.agent.model;

import com.free.agent.field.DayOfWeek;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by antonPC on 09.01.16.
 */
@Getter
@Setter
@NoArgsConstructor
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

}
