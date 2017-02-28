package com.free.agent.converter;

import com.free.agent.field.DayOfWeek;
import com.free.agent.model.Weekday;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Created by antonPC on 28.02.17.
 */
@Component
public class String2Weekday implements Converter<String, Weekday> {

    @Override
    public Weekday convert(String input) {
        Weekday weekday = new Weekday();
        weekday.setDayOfWeek(DayOfWeek.valueOf(input));
        return weekday;
    }
}
