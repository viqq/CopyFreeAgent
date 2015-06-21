package com.free.agent.model;


import com.free.agent.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGIN", unique = true)
    private String login;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CITY", nullable = true)
    private String city;

    @Column(name = "EMAIL", nullable = true)
    private String email;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "USER_SPORT",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SPORT_ID")})
    private Set<Sport> sports = new HashSet<Sport>();

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role = Role.ROLE_MODERATOR;;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Sport> getSports() {
        return sports;
    }

    public void setSports(Set<Sport> sports) {
        this.sports = sports;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
