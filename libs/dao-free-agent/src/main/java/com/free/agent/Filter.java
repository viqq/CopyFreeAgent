package com.free.agent;

import com.free.agent.model.Sport;
import com.free.agent.model.User;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * Created by antonPC on 03.07.15.
 */
public final class Filter {

    private String sport;
    private String firstName;
    private String lastName;
    private Date dateOfBirthFrom;
    private Date dateOfBirthTo;

    //TODO
    public Predicate getPredicate(CriteriaBuilder cb, CriteriaQuery<User> query) {
        Root<User> fromUser = query.from(User.class);
        Path<Sport> fromSport = query.from(Sport.class);
        return null;
    }

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
