package com.free.agent;

import com.free.agent.model.User;
import com.free.agent.model.User_;
import com.free.agent.utils.PredicateBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

/**
 * Created by antonPC on 03.07.15.
 */
public final class Filter {

    private String sport;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirthFrom;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirthTo;

    //TODO
    public Predicate getPredicate(CriteriaBuilder cb, CriteriaQuery<User> query) {
        Root<User> fromUser = query.from(User.class);
        //Root<Sport> fromSport = query.from(Sport.class);
        return new PredicateBuilder(cb)
                .addEqualsPredicate(cb, fromUser.get(User_.firstName), getFirstName().equals("") ? null : getFirstName())
                .addEqualsPredicate(cb, fromUser.get(User_.lastName), getLastName().equals("") ? null : getLastName())
                .addRangePredicate(cb, fromUser.get(User_.dateOfBirth), getDateOfBirthFrom(), getDateOfBirthTo())
                        //.add( sport, fromUser.get(User_.sports))
                .buildWithAndConjunction();

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
