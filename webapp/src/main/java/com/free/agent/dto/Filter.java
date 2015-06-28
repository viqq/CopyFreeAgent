package com.free.agent.dto;

import java.util.Date;

/**
 * Created by antonPC on 28.06.15.
 */
public final class Filter {
    private String sport;
    private String firstName;
    private String lastName;
    private Date dateOfBirthFrom;
    private Date dateOfBirthTo;

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
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

    public Date getDateOfBirthFrom() {
        return dateOfBirthFrom;
    }

    public void setDateOfBirthFrom(Date dateOfBirthFrom) {
        this.dateOfBirthFrom = dateOfBirthFrom;
    }

    public Date getDateOfBirthTo() {
        return dateOfBirthTo;
    }

    public void setDateOfBirthTo(Date dateOfBirthTo) {
        this.dateOfBirthTo = dateOfBirthTo;
    }
}
