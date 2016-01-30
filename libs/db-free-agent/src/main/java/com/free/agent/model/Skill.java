package com.free.agent.model;

import com.free.agent.field.SkillLevel;

import javax.persistence.*;

/**
 * Created by antonPC on 12.01.16.
 */
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

    @Override
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

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }
}
