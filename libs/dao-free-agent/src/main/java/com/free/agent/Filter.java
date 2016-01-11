package com.free.agent;

import com.free.agent.model.Sport;
import com.free.agent.model.Sport_;
import com.free.agent.model.User;
import com.free.agent.model.User_;
import com.free.agent.utils.PredicateBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * Created by antonPC on 03.07.15.
 */
//todo remove
public final class Filter {
    private String sport;
    private String firstName;
    private String lastName;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirthFrom;
    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date dateOfBirthTo;

    public Predicate getPredicate(CriteriaBuilder cb, CriteriaQuery<User> query) {
        Root<User> fromUser = query.from(User.class);
        Predicate predicate = null;
        if (validValue(sport) != null) {
            SetJoin<User, Sport> sportName = fromUser.join(User_.sports);
            predicate = cb.equal(sportName.get(Sport_.name), sport);
        }
        return new PredicateBuilder(cb)
                .addEqualsPredicate(cb, fromUser.get(User_.firstName), validValue(firstName))
                .addEqualsPredicate(cb, fromUser.get(User_.lastName), validValue(lastName))
                .addRangePredicate(cb, fromUser.get(User_.dateOfBirth), dateOfBirthFrom, dateOfBirthTo)
                .add(predicate)
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

    public String validValue(String value) {
        return value == null ? null : value.equals("") ? null : value;
    }
}
