package com.free.agent.utils;

import com.free.agent.model.Sport;
import com.google.common.base.Function;

/**
 * Created by antonPC on 19.08.15.
 */
public final class ExtractFunction {

    public static final Function<Sport, String> SPORT_NAME_INVOKE = new Function<Sport, String>() {
        @Override
        public String apply(Sport input) {
            return input.getName();
        }
    };

}
