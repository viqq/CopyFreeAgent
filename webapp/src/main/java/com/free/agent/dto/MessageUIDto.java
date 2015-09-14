package com.free.agent.dto;

import java.util.Date;

/**
 * Created by antonPC on 15.09.15.
 */
public class MessageUIDto {
    private Long id;
    private String author;
    private String title;
    private String text;
    private Date timeOfCreate;
    private Date timeOfRead;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
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
        return timeOfCreate;
    }

    public void setTimeOfCreate(Date timeOfCreate) {
        this.timeOfCreate = timeOfCreate;
    }

    public Date getTimeOfRead() {
        return timeOfRead;
    }

    public void setTimeOfRead(Date timeOfRead) {
        this.timeOfRead = timeOfRead;
    }
}
