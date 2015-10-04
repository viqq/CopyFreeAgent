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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Participant that = (Participant) o;

        if (authorId != null ? !authorId.equals(that.authorId) : that.authorId != null) return false;
        return !(authorEmail != null ? !authorEmail.equals(that.authorEmail) : that.authorEmail != null);

    }

    @Override
    public int hashCode() {
        int result = authorId != null ? authorId.hashCode() : 0;
        result = 31 * result + (authorEmail != null ? authorEmail.hashCode() : 0);
        return result;
    }
}
