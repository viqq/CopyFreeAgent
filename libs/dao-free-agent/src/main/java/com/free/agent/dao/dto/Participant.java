package com.free.agent.dao.dto;

/**
 * Created by antonPC on 01.10.15.
 */
public class Participant {
    private Long authorId;
    private String authorEmail;

    public Participant(Long authorId, String authorEmail) {
        this.authorId = authorId;
        this.authorEmail = authorEmail;
    }

    public Participant() {
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorEmail() {
        return authorEmail;
    }

    public void setAuthorEmail(String authorEmail) {
        this.authorEmail = authorEmail;
    }
}
