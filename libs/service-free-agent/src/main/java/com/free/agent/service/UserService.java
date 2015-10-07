package com.free.agent.service;

import com.free.agent.Filter;
import com.free.agent.model.User;
import com.free.agent.service.dto.UserDto;
import com.free.agent.service.dto.UserWithSportUIDto;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Set;

/**
 * Created by antonPC on 21.06.15.
 */
public interface UserService {
    User save(User user);

    void addImage(String email, HttpServletRequest request) throws Exception;

    Collection<User> findAll();

    User findById(Long id);

    Collection<UserWithSportUIDto> findByFilter(Filter filter);

    void deleteUser(Long id);

    void editUser(Long id, UserDto userDto, Set<String> sports);

    UserWithSportUIDto getInfoAboutUser(String email);

    UserWithSportUIDto getInfoAboutUserById(Long id);
}
