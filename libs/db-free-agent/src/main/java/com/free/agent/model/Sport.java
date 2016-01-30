package com.free.agent.model;

import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by antonPC on 15.06.15.
 */
@Entity
@Table(name = "SPORT", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME_EN", "NAME_RU"})})
public class Sport extends AbstractTable<Long> {

    @Id
    @Column(name = "SPORT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME_EN", unique = true)
    private String nameEn;

    @Column(name = "NAME_RU", unique = true)
    private String nameRu;

    @ManyToMany(mappedBy = "sports", fetch = FetchType.LAZY)
    private Set<User> users = Sets.newHashSet();

    public Sport(String nameEn, String nameRu) {
        this.nameEn = nameEn;
        this.nameRu = nameRu;
    }

    public Sport() {
    }

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
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

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
