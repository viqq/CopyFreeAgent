package com.free.agent.model;

import org.apache.commons.lang3.ObjectUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by antonPC on 28.07.15.
 */
@Entity
@Table(name = "MESSAGE")
public class Message extends AbstractTable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_ID")
    private Long id;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TEXT")
    private String text;

    @Column(name = "TIME_OF_CREATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfCreate;

    @Column(name = "TIME_OF_READ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeOfRead;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID")
    private User user;

    public Message() {
    }

    public Message(String author, String title, String text) {
        this.author = author;
        this.title = title;
        this.text = text;
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String isTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimeOfCreate() {
        return ObjectUtils.clone(timeOfCreate);
    }

    public void setTimeOfCreate(Date timeOfCreate) {
        this.timeOfCreate = ObjectUtils.clone(timeOfCreate);
    }

    public Date getTimeOfRead() {
        return ObjectUtils.clone(timeOfRead);
    }

    public void setTimeOfRead(Date timeOfRead) {
        this.timeOfRead = ObjectUtils.clone(timeOfRead);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
