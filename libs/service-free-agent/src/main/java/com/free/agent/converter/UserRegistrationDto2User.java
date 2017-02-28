package com.free.agent.converter;

import com.free.agent.dto.UserRegistrationDto;
import com.free.agent.field.Role;
import com.free.agent.model.User;
import com.free.agent.util.EncryptionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by antonPC on 28.02.17.
 */
@Component
public class UserRegistrationDto2User implements Converter<UserRegistrationDto, User> {

    @Override
    public User convert(UserRegistrationDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(EncryptionUtils.encrypt(userDto.getPassword()));
        user.setFirstName(userDto.getFirstName());
        user.setDateOfRegistration(new Date());
        user.setHash(EncryptionUtils.getRandomString());
        user.setLastActivity(new Date());
        user.setRole(Role.ROLE_NOT_CONFIRMED);
        return user;
    }
}
