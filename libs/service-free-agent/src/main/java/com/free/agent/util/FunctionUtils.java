package com.free.agent.util;

import com.free.agent.dto.*;
import com.free.agent.dto.network.SocialProfile;
import com.free.agent.field.DayOfWeek;
import com.free.agent.field.Gender;
import com.free.agent.field.Role;
import com.free.agent.model.*;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.sql.Time;
import java.util.*;

/**
 * Created by antonPC on 19.08.15.
 */
public final class FunctionUtils {

    public static final Function<Sport, SportUIDto> SPORT_NAME_INVOKE = new Function<Sport, SportUIDto>() {
        @Override
        public SportUIDto apply(Sport input) {
            SportUIDto dto = new SportUIDto();
            dto.setId(input.getId());
            dto.setNameEn(input.getNameEn());
            dto.setNameRu(input.getNameRu());
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

    public static final Function<User, FavoriteDto> FAVORITE_INVOKE = new Function<User, FavoriteDto>() {
        @Override
        public FavoriteDto apply(User input) {
            FavoriteDto dto = new FavoriteDto();
            dto.setUserId(input.getId());
            dto.setUserEmail(input.getEmail());
            return dto;
        }
    };

    public static final Function<Long, Day> DAY_INVOKE = new Function<Long, Day>() {
        @Override
        public Day apply(Long input) {
            DateTime dateTime = new DateTime((long) input);
            String dayOfWeek = new DateTime().dayOfWeek().getAsText(Locale.ENGLISH).toUpperCase();
            return new Day(dateTime.toDate(), DayOfWeek.valueOf(dayOfWeek));
        }
    };

    public static final Function<String, Weekday> WEEKDAY_INVOKE = new Function<String, Weekday>() {
        @Override
        public Weekday apply(String input) {
            return new Weekday(DayOfWeek.valueOf(input));
        }
    };

    public static final Function<User, UserWithScheduleUIDto> USER_WITH_SCHEDULES_INVOKE = new Function<User, UserWithScheduleUIDto>() {
        @Override
        public UserWithScheduleUIDto apply(User input) {
            return getUserWithScheduleForUI(input);
        }
    };

    public static final Comparator<UserWithScheduleUIDto> USER_WITH_SCHEDULES_COMPARATOR = new Comparator<UserWithScheduleUIDto>() {
        @Override
        public int compare(UserWithScheduleUIDto u1, UserWithScheduleUIDto u2) {
            return 0; //todo
        }
    };

    public static final Function<Schedule, ScheduleDto> SCHEDULE_INVOKE = new Function<Schedule, ScheduleDto>() {
        @Override
        public ScheduleDto apply(Schedule input) {
            ScheduleDto dto = new ScheduleDto();
            dto.setId(input.getId());
            dto.setSportId(input.getSport().getId());
            dto.setStartTime(input.getStartTime().getTime());
            dto.setEndTime(input.getEndTime().getTime());
            dto.setDayOfWeeks(FluentIterable.from(input.getWeekdays()).transform(new Function<Weekday, String>() {
                @Override
                public String apply(Weekday input) {
                    return input.getDayOfWeek().name();
                }
            }).toSet());
            dto.setDays(FluentIterable.from(input.getDays()).transform(new Function<Day, Long>() {
                @Override
                public Long apply(Day input) {
                    return input.getDate().getTime();
                }
            }).toSet());
            return dto;
        }
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
        userDto.setSports(FluentIterable.from(user.getSports()).transform(SPORT_NAME_INVOKE).toList());
        userDto.setSchedules(FluentIterable.from(user.getSchedules()).transform(SCHEDULE_INVOKE).toList());
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
        userDto.setSports(FluentIterable.from(user.getSports()).transform(SPORT_NAME_INVOKE).toList());
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

    public static User getUser(SocialProfile profile) {
        User user = new User();
        user.setEmail(profile.getEmail());
        user.setFirstName(profile.getFirstName());
        user.setDateOfRegistration(new Date());
        user.setLastActivity(new Date());
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

    private static Long getTime(Date dateOfBirth) {
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
        List<GrantedAuthority> authorities = Lists.newArrayList();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
