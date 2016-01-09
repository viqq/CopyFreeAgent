package com.free.agent;

import com.free.agent.field.Weekday;
import com.free.agent.model.*;
import com.free.agent.utils.PredicateBuilder;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import org.apache.commons.collections.CollectionUtils;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by antonPC on 07.01.16.
 */
public final class FilterNew {
    private String city;
    private String country;
    private Long dateOfBirthFrom;
    private Long dateOfBirthTo;
    private String firstName;
    private String lastName;
    private Set<String> sports;
    private Set<Long> days;
    private Set<String> weekdays;
    private Long fromTime;
    private Long endTime;


    public Predicate getPredicate(CriteriaBuilder cb, CriteriaQuery<User> query) {
        Root<User> fromUser = query.from(User.class);
        Predicate predicate = null;
        SetJoin<User, Sport> fromSport = fromUser.join(User_.sports);
        ListJoin<User, Schedule> fromSchedule = fromUser.join(User_.schedules);
        SetJoin<Schedule, Day> fromDay = fromSchedule.join(Schedule_.days);
        SetJoin<Schedule, Weekday> fromWeekday = fromSchedule.joinSet("weekdays");

        if (!CollectionUtils.isEmpty(days)) {
            predicate = cb.or(fromDay.get(Day_.date).in(Collections2.transform(days, new Function<Long, Date>() {
                @Override
                public Date apply(Long input) {
                    return new Date(input);
                }
            }))); //todo
        }
        if (!CollectionUtils.isEmpty(weekdays)) {
            Set<Weekday> weekdaySet = FluentIterable.from(weekdays).transform(new Function<String, Weekday>() {
                @Override
                public Weekday apply(String input) {
                    return Weekday.valueOf(input);
                }
            }).toSet();
            predicate = cb.or(
                    fromDay.get(Day_.weekday).in(weekdaySet),
                    fromSchedule.get(Schedule_.weekdays).in(weekdaySet));
        }

        return new PredicateBuilder(cb)
                .addEqualsPredicate(cb, fromUser.get(User_.city), validValue(city))
                .addEqualsPredicate(cb, fromUser.get(User_.country), validValue(country))
                .addEqualsPredicate(cb, fromUser.get(User_.firstName), validValue(firstName))
                .addEqualsPredicate(cb, fromUser.get(User_.lastName), validValue(lastName))
                .addRangePredicate(cb, fromUser.get(User_.dateOfBirth), validValue(dateOfBirthFrom), validValue(dateOfBirthTo))
                .addInPredicate(fromSport.get(Sport_.name), sports)
                .add(predicate)
                .buildWithAndConjunction();
    }

    private Date validValue(Long dateOfBirthFrom) {
        return dateOfBirthFrom == null ? null : new Date(dateOfBirthFrom);
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getDateOfBirthFrom() {
        return dateOfBirthFrom;
    }

    public void setDateOfBirthFrom(Long dateOfBirthFrom) {
        this.dateOfBirthFrom = dateOfBirthFrom;
    }

    public Long getDateOfBirthTo() {
        return dateOfBirthTo;
    }

    public void setDateOfBirthTo(Long dateOfBirthTo) {
        this.dateOfBirthTo = dateOfBirthTo;
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

    public Set<String> getSports() {
        return sports;
    }

    public void setSports(Set<String> sports) {
        this.sports = sports;
    }

    public Set<Long> getDays() {
        return days;
    }

    public void setDays(Set<Long> days) {
        this.days = days;
    }

    public Set<String> getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(Set<String> weekdays) {
        this.weekdays = weekdays;
    }

    public Long getFromTime() {
        return fromTime;
    }

    public void setFromTime(Long fromTime) {
        this.fromTime = fromTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String validValue(String value) {
        return value == null ? null : value.equals("") ? null : value;
    }

}
