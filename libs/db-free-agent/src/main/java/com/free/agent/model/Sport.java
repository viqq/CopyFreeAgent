package com.free.agent.model;

import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Entity
@Table(name = "SPORT", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
public class Sport extends AbstractTable<Long> {

    @Id
    @Column(name = "SPORT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "IMAGE")
    private String image;

    @ManyToMany(mappedBy = "sports", fetch = FetchType.LAZY)
    private Set<User> users = Sets.newHashSet();

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

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
