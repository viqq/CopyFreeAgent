package com.free.agent.service.util;

import com.free.agent.field.Role;
import com.free.agent.model.Message;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.free.agent.service.dto.MessageUIDto;
import com.free.agent.service.dto.SportUIDto;
import com.free.agent.service.dto.UserRegistrationDto;
import com.free.agent.service.dto.UserWithSportUIDto;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.Date;

/**
 * Created by antonPC on 19.08.15.
 */
public final class ExtractFunction {

    public static final Function<Sport, SportUIDto> SPORT_NAME_INVOKE = new Function<Sport, SportUIDto>() {
        @Override
        public SportUIDto apply(Sport input) {
            SportUIDto dto = new SportUIDto();
            dto.setId(input.getId());
            dto.setImage(input.getImage());
            dto.setName(input.getName());
            return dto;
        }
    };

    public static final Function<Message, MessageUIDto> MESSAGE_INVOKE = new Function<Message, MessageUIDto>() {
        @Override
        public MessageUIDto apply(Message input) {
            MessageUIDto message = new MessageUIDto();
            message.setId(input.getId());
            message.setTimeOfRead(getTime(input.getTimeOfRead()));
            message.setTimeOfCreate(getTime(input.getTimeOfCreate()));
            message.setTitle(input.getTitle());
            message.setText(input.getText());
            message.setAuthorId(input.getAuthorId());
            return message;
        }
    };

    public static UserWithSportUIDto getUserForUI(User user) {
        UserWithSportUIDto userDto = new UserWithSportUIDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setDescription(user.getDescription());
        userDto.setCity(user.getCity());
        userDto.setDateOfBirth(getTime(user.getDateOfBirth()));
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setDateOfRegistration(getTime(user.getDateOfRegistration()));
        userDto.setGender(user.getGender().name());
        userDto.setRole(user.getRole().name());
        userDto.setSports(Lists.transform(Lists.newArrayList(user.getSports()), ExtractFunction.SPORT_NAME_INVOKE));
        return userDto;
    }

    public static User getUser(UserRegistrationDto userDto) {
        User user = new User(userDto.getEmail(), EncryptionUtils.encrypt(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setDateOfRegistration(new Date());
        user.setHash(EncryptionUtils.getRandomString());
        user.setLastActivity(new Date());
        user.setRole(Role.ROLE_NOT_CONFIRMED);
        return user;
    }

    private static Long getTime(Date dateOfBirth) {
        return dateOfBirth == null ? null : dateOfBirth.getTime();
    }

}
