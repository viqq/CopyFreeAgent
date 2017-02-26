package com.free.agent.model;

import com.free.agent.field.SkillLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by antonPC on 12.01.16.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SKILL")
public class Skill extends AbstractTable<Long> {

    @Id
    @Column(name = "SKILL_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "SPORT_ID")
    private Sport sport;

    @Column(name = "SKILL_LEVEL", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private SkillLevel skillLevel = SkillLevel.BEGINNER;

}
