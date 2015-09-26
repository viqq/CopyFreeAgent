package com.free.agent.utils;

import com.free.agent.model.Message;
import com.free.agent.model.Sport;
import com.free.agent.model.User;
import com.free.agent.service.dto.MessageUIDto;
import com.free.agent.service.dto.UserRegistrationDto;
import com.free.agent.service.dto.UserWithSport;
import com.free.agent.service.util.EncryptionUtils;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.Date;

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

    public static final Function<Message, MessageUIDto> MESSAGE_INVOKE = new Function<Message, MessageUIDto>() {
        @Override
        public MessageUIDto apply(Message input) {
            MessageUIDto message = new MessageUIDto();
            message.setId(input.getId());
            message.setTimeOfRead(input.getTimeOfRead());
            message.setTimeOfCreate(input.getTimeOfCreate());
            message.setTitle(input.getTitle());
            message.setText(input.getText());
            message.setAuthorId(input.getAuthorId());
            message.setAuthorEmail(input.getAuthorEmail());
            return message;
        }
    };

    public static UserWithSport getUserForUI(User user) {
        UserWithSport userDto = new UserWithSport();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setPhone(user.getPhone());
        userDto.setDescription(user.getDescription());
        userDto.setCity(user.getCity());
        userDto.setDateOfBirth(user.getDateOfBirth());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setDateOfRegistration(user.getDateOfRegistration());
        userDto.setSports(Lists.transform(Lists.newArrayList(user.getSports()), ExtractFunction.SPORT_NAME_INVOKE));
        return userDto;
    }

    public static User getUser(UserRegistrationDto userDto) {
        User user = new User(userDto.getEmail(), EncryptionUtils.encrypt(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setDateOfRegistration(new Date());
        return user;
    }

}
