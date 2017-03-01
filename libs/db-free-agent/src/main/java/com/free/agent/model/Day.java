package com.free.agent.model;

import com.free.agent.field.DayOfWeek;
import lombok.*;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by antonPC on 29.12.15.
 */
@Getter
@Setter
@NoArgsConstructor
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
    @Column(name = "DAY_OF_WEEK")
    private DayOfWeek dayOfWeek;

}
