package com.free.agent.converter;

import com.free.agent.dto.UserWithSportUIDto;
import com.free.agent.field.Gender;
import com.free.agent.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;


/**
 * Created by antonPC on 28.02.17.
 */
@Component
public class User2UserWithSportUIDto implements Converter<User, UserWithSportUIDto> {

    @Autowired
    private Sport2SportUIDto sport2SportUIDto;

    @Override
    public UserWithSportUIDto convert(User user) {
        UserWithSportUIDto userDto = new UserWithSportUIDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setDescription(user.getDescription());
        userDto.setCity(user.getCity());
        userDto.setCountry(user.getCountry());
        userDto.setDateOfBirth(getTime(user.getDateOfBirth()));
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setDateOfRegistration(getTime(user.getDateOfRegistration()));
        userDto.setGender(getGender(user.getGender()));
        userDto.setRole(user.getRole().name());
        userDto.setSports(user.getSports().stream().map(sport2SportUIDto::convert).collect(Collectors.toList()));
        return userDto;
    }

    private static String getGender(Gender gender) {
        return gender == null ? null : gender.name();
    }

    private static Long getTime(Date dateOfBirth) {
        return dateOfBirth == null ? null : dateOfBirth.getTime();
    }

}
