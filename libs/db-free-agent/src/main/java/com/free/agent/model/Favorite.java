package com.free.agent.model;

import javax.persistence.*;

/**
 * Created by antonPC on 06.12.15.
 */
@Entity
@Table(name = "FAVORITE", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID"}),
        @UniqueConstraint(columnNames = {"FAVORITE_USER_ID"})})
public class Favorite extends AbstractTable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAVORITE_ID")
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "FAVORITE_USER_ID", referencedColumnName = "USER_ID")
    private User favoriteUser;

    @Column(name = "COMMENT")
    private String comment;

    public Favorite(User user, User favoriteUser, String comment) {
        this.user = user;
        this.favoriteUser = favoriteUser;
        this.comment = comment;
    }

    public Favorite() {
    }

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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getFavoriteUser() {
        return favoriteUser;
    }

    public void setFavoriteUser(User favoriteUser) {
        this.favoriteUser = favoriteUser;
    }
}
