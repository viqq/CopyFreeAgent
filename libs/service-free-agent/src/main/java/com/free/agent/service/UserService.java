package com.free.agent.service;

import com.free.agent.Filter;
import com.free.agent.dto.UserDto;
import com.free.agent.dto.UserRegistrationDto;
import com.free.agent.dto.UserWithSportUIDto;
import com.free.agent.exception.EmailAlreadyUsedException;
import com.free.agent.exception.WrongLinkException;
import com.free.agent.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Set;

/**
 * Created by antonPC on 21.06.15.
 */
public interface UserService {
    User save(UserRegistrationDto userDto) throws EmailAlreadyUsedException;

    void addImage(String email, HttpServletRequest request) throws Exception;

    Collection<User> findAll();

    User findById(Long id);

    Collection<UserWithSportUIDto> findByFilter(Filter filter);

    void deleteUser(Long id);

    void editUser(Long id, UserDto userDto, Set<String> sports);

    UserWithSportUIDto getInfoAboutUser(String email);

    UserWithSportUIDto getInfoAboutUserById(Long id);

    UserWithSportUIDto activateUser(String hash, String key) throws WrongLinkException;

    void resetPassword(String email) throws IllegalArgumentException;

    String getPostponeEmail(String hash, String key);
}
