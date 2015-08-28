package com.free.agent.utils;

import com.free.agent.dto.UserWithSport;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

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

    public static UserWithSport getUserForUI(User user) {
        UserWithSport userDto = new UserWithSport();
        userDto.setId(user.getId());
        userDto.setLogin(user.getLogin());
        userDto.setPhone(user.getPhone());
        userDto.setDescription(user.getDescription());
        userDto.setCity(user.getCity());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setDateOfRegistration(user.getDateOfRegistration());
        userDto.setSports(Lists.transform(Lists.newArrayList(user.getSports()), ExtractFunction.SPORT_NAME_INVOKE));
        return userDto;
    }

}
