package com.free.agent;

import com.free.agent.model.Sport;
import com.free.agent.model.Sport_;
import com.free.agent.model.User;
import com.free.agent.model.User_;
import com.free.agent.utils.PredicateBuilder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.criteria.*;
import java.util.Date;

/**
 * Created by antonPC on 03.07.15.
 */
@Data
public final class Filter {
    //todo add ability to many sports
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
            predicate = cb.equal(sportName.get(Sport_.nameEn), getSport());
        }
        return new PredicateBuilder(cb)
                .addEqualsPredicate(cb, fromUser.get(User_.firstName), validValue(firstName))
                .addEqualsPredicate(cb, fromUser.get(User_.lastName), validValue(lastName))
                .addRangePredicate(cb, fromUser.get(User_.dateOfBirth), dateOfBirthFrom, dateOfBirthTo)
                .add(predicate)
                .buildWithAndConjunction();
    }

    private String validValue(String value) {
        return value == null ? null : value.equals("") ? null : value;
    }
}
