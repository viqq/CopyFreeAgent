package com.free.agent.util;

import com.free.agent.dto.*;
import com.free.agent.dto.network.SocialProfile;
import com.free.agent.field.Gender;
import com.free.agent.field.Role;
import com.free.agent.model.Schedule;
import com.free.agent.model.User;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Time;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antonPC on 19.08.15.
 */
public final class FunctionUtils {

    public static final Comparator<UserWithScheduleUIDto> USER_WITH_SCHEDULES_COMPARATOR = (u1, u2) -> {
        return 0; //todo
    };


    public static UserWithScheduleUIDto getUserWithScheduleForUI(User user) {
        UserWithScheduleUIDto userDto = new UserWithScheduleUIDto();
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
        userDto.setSports(user.getSports().stream().map(input -> {
            SportUIDto dto = new SportUIDto();
            dto.setId(input.getId());
            dto.setNameEn(input.getNameEn());
            dto.setNameRu(input.getNameRu());
            return dto;
        }).collect(Collectors.toList()));
        userDto.setSchedules(user.getSchedules().stream().map(input -> {
            ScheduleDto dto = new ScheduleDto();
            dto.setId(input.getId());
            dto.setSportId(input.getSport().getId());
            dto.setStartTime(input.getStartTime().getTime());
            dto.setEndTime(input.getEndTime().getTime());
            dto.setDayOfWeeks(FluentIterable.from(input.getWeekdays()).transform(input1 -> input1.getDayOfWeek().name()).toSet());
            dto.setDays(FluentIterable.from(input.getDays()).transform(input12 -> input12.getDate().getTime()).toSet());
            return dto;
        }).collect(Collectors.toList()));
        return userDto;
    }

    public static UserWithSportUIDto getUserForUI(User user) {
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
        userDto.setSports(user.getSports().stream().map(input -> {
            SportUIDto dto = new SportUIDto();
            dto.setId(input.getId());
            dto.setNameEn(input.getNameEn());
            dto.setNameRu(input.getNameRu());
            return dto;
        }).collect(Collectors.toList()));
        return userDto;
    }

    public static User getUser(UserRegistrationDto userDto) {
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

    public static User getUser(SocialProfile profile) {
        User user = new User();
        user.setEmail(profile.getEmail());
        user.setFirstName(profile.getFirstName());
        user.setDateOfRegistration(DateTime.now().toDate());
        user.setLastActivity(DateTime.now().toDate());
        user.setCity(profile.getCity());
        user.setCountry(profile.getCountry());
        user.setLastName(profile.getLastName());
        user.setGender(profile.getGender());
        user.setDateOfBirth(profile.getBirthday());
        setType(user, profile);
        setRole(user, profile.isVerified());
        return user;
    }

    public static Schedule getSchedule(ScheduleDto scheduleDto) {
        Schedule schedule = new Schedule();
        schedule.setStartTime(new Time(scheduleDto.getStartTime()));
        schedule.setEndTime(new Time(scheduleDto.getEndTime()));
        return schedule;
    }

    public static Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return getGrantedAuthorities(getRoles(role));
    }

    private static void setRole(User user, boolean verified) {
        if (verified) {
            user.setRole(Role.ROLE_MODERATOR);
        } else {
            user.setRole(Role.ROLE_NOT_CONFIRMED);
            user.setHash(EncryptionUtils.getRandomString());
        }
    }

    private static void setType(User user, SocialProfile profile) {
        switch (profile.getType()) {
            case GOOGLE: {
                user.setGoogleId(profile.getId());
                break;
            }
            case VK: {
                user.setVkId(profile.getId());
                break;
            }
            case FACEBOOK: {
                user.setFacebookId(profile.getId());
                break;
            }
            default: {
                throw new UnsupportedOperationException("Unsupported type of social network");
            }
        }
    }

    private static String getGender(Gender gender) {
        return gender == null ? null : gender.name();
    }

    public static Long getTime(Date dateOfBirth) {
        return dateOfBirth == null ? null : dateOfBirth.getTime();
    }

    private static List<String> getRoles(Role role) {
        List<String> roles = Lists.newArrayList();
        switch (role) {
            case ROLE_ADMIN: {
                roles.add(Role.ROLE_ADMIN.name());
                roles.add(Role.ROLE_MODERATOR.name());
                roles.add(Role.ROLE_NOT_CONFIRMED.name());
                roles.add(Role.ROLE_NOT_ACTIVATED.name());
            }
            case ROLE_MODERATOR: {
                roles.add(Role.ROLE_MODERATOR.name());
                roles.add(Role.ROLE_NOT_CONFIRMED.name());
                roles.add(Role.ROLE_NOT_ACTIVATED.name());
            }
            case ROLE_NOT_CONFIRMED: {
                roles.add(Role.ROLE_NOT_CONFIRMED.name());
                roles.add(Role.ROLE_NOT_ACTIVATED.name());
            }
            default: {
                roles.add(Role.ROLE_NOT_ACTIVATED.name());
            }
        }
        return roles;
    }

    private static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}
