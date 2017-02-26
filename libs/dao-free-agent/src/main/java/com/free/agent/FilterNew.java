package com.free.agent;

import com.free.agent.field.DayOfWeek;
import com.free.agent.model.*;
import com.free.agent.utils.PredicateBuilder;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;

import javax.persistence.criteria.*;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by antonPC on 07.01.16.
 */
@Data
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
            Set<Date> allDates = days.stream().map(input -> new DateTime(input).toDate()).collect(Collectors.toSet());
            Set<DayOfWeek> allDays = days.stream().map(input -> DayOfWeek.valueOf(new DateTime(input).dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase())).collect(Collectors.toSet());
            dayPredicate = cb.or(
                    fromDay.get(Day_.date).in(allDates),
                    fromWeekday.get(Weekday_.dayOfWeek).in(allDays));
        }

        if (!CollectionUtils.isEmpty(weekdays)) {
            Set<DayOfWeek> dayOfWeekSet = weekdays.stream().map(DayOfWeek::valueOf).collect(Collectors.toSet());
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
        return dateOfBirthFrom == null ? null : new DateTime(dateOfBirthFrom).toDate();
    }

    private String validValue(String value) {
        return value == null ? null : value.equals("") ? null : value;
    }
}
