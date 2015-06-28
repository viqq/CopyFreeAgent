package com.free.agent.model;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Entity
@Table(name = "SPORT", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
public class Sport implements Serializable {

    @Id
    @Column(name = "SPORT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "NAME", unique = true)
    private String name;
    @ManyToMany(mappedBy = "sports")
    private Set<User> users = new HashSet<User>();

    public Sport(String name) {
        this.name = name;
    }

    public Sport() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sport that = (Sport) o;
        return Objects.equal(this.id, that.id) &&
                Objects.equal(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, users);
    }
}
