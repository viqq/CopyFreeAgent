package com.free.agent.model;


import com.free.agent.field.Gender;
import com.free.agent.field.Role;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"EMAIL"})})
public class User extends AbstractTable<Long> {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role = Role.ROLE_MODERATOR;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
    private Gender gender = Gender.MALE;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DATE_OF_BIRTH")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "IMAGE")
    private String image;

    @Column(name = "VK_ID")
    private Long vkId;

    @Column(name = "FACEBOOK_ID")
    private Long facebookId;

    @Column(name = "GOOGLE_ID")
    private Long googleId;

    @Column(name = "HASH")
    private String hash;

    @Column(name = "DATE_OF_REGISTRATION")
    @Temporal(TemporalType.DATE)
    private Date dateOfRegistration;

    @Column(name = "LAST_ACTIVITY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActivity;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_SPORT",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SPORT_ID")})
    private Set<Sport> sports = Sets.newHashSet();

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(name = "USER_FAVORITE",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "FAVORITE_ID")})
    private Set<User> favorites = Sets.newHashSet();

    @ManyToMany(mappedBy = "favorites")
    private Set<User> followers = Sets.newHashSet();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Schedule> schedules = Lists.newArrayList();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Skill> skills = Sets.newHashSet();

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "user")
    private List<Message> messages = Lists.newArrayList();

}
