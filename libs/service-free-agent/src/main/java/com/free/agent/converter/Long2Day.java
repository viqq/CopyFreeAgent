package com.free.agent.converter;

import com.free.agent.field.DayOfWeek;
import com.free.agent.model.Day;
import org.joda.time.DateTime;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * Created by antonPC on 28.02.17.
 */
@Component
public class Long2Day implements Converter<Long, Day> {

    @Override
    public Day convert(Long input) {
        DateTime dateTime = new DateTime((long) input);
        String dayOfWeek = new DateTime().dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase();
        Day day = new Day();
        day.setDate(dateTime.toDate());
        day.setDayOfWeek(DayOfWeek.valueOf(dayOfWeek));
        return day;
    }
}
