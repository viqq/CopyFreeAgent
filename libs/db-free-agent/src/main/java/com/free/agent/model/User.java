package com.free.agent.model;


import com.free.agent.role.Role;
import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Entity
@Table(name = "USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"LOGIN"})})
public class User implements Serializable {

    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "LOGIN", nullable = false, unique = true)
    private String login;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "CITY")
    private String city;

    @Column(name = "EMAIL")
    private String email;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_SPORT",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "SPORT_ID")})
    private Set<Sport> sports = new HashSet<Sport>();

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Role role = Role.ROLE_MODERATOR;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "PHONE", nullable = false, unique = true)
    private String phone;

    public User(String login, String password, String phone) {
        this.login = login;
        this.password = password;
        this.phone = phone;
    }

    public User() {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.login, that.login) &&
                Objects.equal(this.password, that.password) &&
                Objects.equal(this.city, that.city) &&
                Objects.equal(this.email, that.email) &&
                Objects.equal(this.sports, that.sports) &&
                Objects.equal(this.role, that.role) &&
                Objects.equal(this.description, that.description) &&
                Objects.equal(this.firstName, that.firstName) &&
                Objects.equal(this.lastName, that.lastName) &&
                Objects.equal(this.dateOfBirth, that.dateOfBirth) &&
                Objects.equal(this.phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, login, password, city, email, sports, role, description, firstName, lastName,
                dateOfBirth, phone);
    }
}
