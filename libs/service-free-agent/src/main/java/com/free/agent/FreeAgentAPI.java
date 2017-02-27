package com.free.agent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Created by antonPC on 18.08.15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FreeAgentAPI {
    public static final String OK = "OK";
    public static final int VALIDATION_ERROR = 460;
    public static final int LOGIN_ERROR = 461;
    public static final int SAVE_IMAGE_ERROR = 462;
    public static final int EMAIL_REGISTERED_ERROR = 463;
    public static final int EMAIL_DID_NOT_REGISTERED_ERROR = 464;
    public static final int ACTIVATED_ERROR = 465;
    public static final int EDIT_PROFILE_ERROR = 466;
    public static final int USER_IS_NOT_FAVORITE_ERROR = 467;
    public static final int EMAIL_IS_NOT_DETECTED_ERROR = 468;
    public static final int UNEXPECTED_ERROR = 499;

    public static final String EMAIL_INVALID = "430";
    public static final String PASSWORD_INVALID = "431";
    public static final String FIRST_NAME_INVALID = "432";

    public static final String SAVE_SPORT = "/api/admin/sport"; //POST
    public static final String SAVE_USER = "/api/user"; //POST
    public static final String EDIT_USER = "/api/user/{id}"; //POST
    public static final String DELETE_USER = "/api/user/{id}"; //DELETE
    public static final String GET_USER_BY_ID = "/api/user/{id}";
    public static final String INFO_ABOUT_USER = "/api/user/info";
    public static final String SAVE_IMAGE = "/api/user/setImage"; //POST
    public static final String GET_IMAGE = "/api/user/getImage/{id}";
    public static final String GET_ALL_SPORTS = "/api/sport";
    public static final String IS_AUTHENTICATION = "/api/isAuthentication";
    public static final String FIND_USER = "/api/search/user"; //POST
    public static final String GET_UNREAD_MESSAGES = "/api/message/unread";
    public static final String SAVE_MESSAGES = "/api/message"; //POST
    public static final String GET_ALL_MESSAGES = "/api/message";
    public static final String GET_SENT_MESSAGES = "/api/message/sent";
    public static final String GET_MESSAGE_BY_ID = "/api/message/{id}";
    public static final String GET_HISTORY = "/api/message/history/{id}";
    public static final String GET_ALL_PARTICIPANTS = "/api/message/participants";
    public static final String ACTIVATE_USER = "/activate";
    public static final String RESET_PASSWORD = "/api/password"; //POST
    public static final String GET_POSTPONE_EMAIL = "/api/postpone";
    public static final String GET_ALL_FAVORITES = "/api/favorite";
    public static final String SAVE_FAVORITE = "/api/favorite/{id}"; //POST
    public static final String DELETE_FAVORITE = "/api/favorite/{id}"; //DELETE
    public static final String REGISTRATION_WITH_VK = "/api/vkregistration";
    public static final String REGISTRATION_WITH_GOOGLE = "/api/ggregistration";
    public static final String REGISTRATION_WITH_FACEBOOK = "/api/fbregistration";
    public static final String LOGIN_WITH_VK = "/api/vklogin";
    public static final String LOGIN_WITH_GOOGLE = "/api/gglogin";
    public static final String LOGIN_WITH_FACEBOOK = "/api/fblogin";
    public static final String SAVE_SCHEDULE = "/api/schedule"; //POST
    public static final String GET_ALL_SCHEDULE = "/api/schedule/{id}";
    public static final String EDIT_SCHEDULE = "/api/schedule/{id}"; //POST
    public static final String DELETE_SCHEDULE = "/api/schedule/{id}"; //DELETE

    public static String host;
}
