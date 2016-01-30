package com.free.agent;

import com.free.agent.field.DayOfWeek;
import com.free.agent.model.*;
import com.free.agent.utils.PredicateBuilder;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

/**
 * Created by antonPC on 07.01.16.
 */
public final class FilterNew {
    //todo only one sport in filter
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

    private final Function<Long, Date> DATE_INVOKE_FROM_TIME = new Function<Long, Date>() {
        @Override
        public Date apply(Long input) {
            return new DateTime(input).toDate();
        }
    };

    private final Function<Long, DayOfWeek> DAY_INVOKE_FROM_TIME = new Function<Long, DayOfWeek>() {
        @Override
        public DayOfWeek apply(Long input) {
            return DayOfWeek.valueOf(new DateTime(input).dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase());
        }
    };

    private final Function<String, DayOfWeek> DAY_INVOKE_FROM_NAME = new Function<String, DayOfWeek>() {
        @Override
        public DayOfWeek apply(String input) {
            return DayOfWeek.valueOf(input);
        }
    };

    public Predicate getPredicate(CriteriaBuilder cb, CriteriaQuery<User> query) {
        Root<User> fromUser = query.from(User.class);
        Predicate dayPredicate = null;
        Predicate weekdayPredicate = null;
        Predicate sportPredicate = null;
        SetJoin<User, Sport> fromSport = fromUser.join(User_.sports, JoinType.LEFT);
        ListJoin<User, Schedule> fromSchedule = fromUser.join(User_.schedules, JoinType.LEFT);
        SetJoin<Schedule, Day> fromDay = fromSchedule.join(Schedule_.days, JoinType.LEFT);
        SetJoin<Schedule, Weekday> fromWeekday = fromSchedule.join(Schedule_.weekdays, JoinType.LEFT);

        if (!CollectionUtils.isEmpty(days)) {
            dayPredicate = cb.or(
                    fromDay.get(Day_.date).in(Collections2.transform(days, DATE_INVOKE_FROM_TIME)),
                    fromWeekday.get(Weekday_.dayOfWeek).in(Collections2.transform(days, DAY_INVOKE_FROM_TIME)));
        }
        if (!CollectionUtils.isEmpty(weekdays)) {
            Set<DayOfWeek> dayOfWeekSet = FluentIterable.from(weekdays).transform(DAY_INVOKE_FROM_NAME).toSet();
            weekdayPredicate = cb.or(
                    fromDay.get(Day_.dayOfWeek).in(dayOfWeekSet),
                    fromWeekday.get(Weekday_.dayOfWeek).in(dayOfWeekSet));
        }
        if (!CollectionUtils.isEmpty(sports)) {
            if (isFindFreeAgent()) {
                sportPredicate = fromSchedule.get(Schedule_.sport).get(Sport_.nameEn).in(sports);
            } else {
                sportPredicate = fromSport.get(Sport_.nameEn).in(sports);
            }
        }

        return new PredicateBuilder(cb)
                .addEqualsPredicate(cb, fromUser.get(User_.city), validValue(city))
                .addEqualsPredicate(cb, fromUser.get(User_.country), validValue(country))
                .addEqualsPredicate(cb, fromUser.get(User_.firstName), validValue(firstName))
                .addEqualsPredicate(cb, fromUser.get(User_.lastName), validValue(lastName))
                .addRangePredicate(cb, fromUser.get(User_.dateOfBirth), validValue(dateOfBirthFrom), validValue(dateOfBirthTo))
                .add(sportPredicate)
                .add(dayPredicate)
                .add(weekdayPredicate)
                .buildWithAndConjunction();
    }

    private boolean isFindFreeAgent() {
        return !CollectionUtils.isEmpty(days) || !CollectionUtils.isEmpty(weekdays) || fromTime != null || endTime != null;
    }

    private Date validValue(Long dateOfBirthFrom) {
        return dateOfBirthFrom == null ? null : new Date(dateOfBirthFrom);
    }

    private String validValue(String value) {
        return value == null ? null : value.equals("") ? null : value;
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

}
