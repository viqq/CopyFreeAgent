package com.free.agent;

/**
 * Created by antonPC on 18.08.15.
 */
public final class FreeAgentAPI {
    public static final String OK = "OK";
    public static final int VALIDATION_ERROR = 460;
    public static final int LOGIN_ERROR = 461;
    public static final int SAVE_IMAGE_ERROR = 462;
    public static final int EMAIL_REGISTERED_ERROR = 463;
    public static final int EMAIL_DID_NOT_REGISTERED_ERROR = 464;
    public static final int ACTIVATED_ERROR = 465;
    public static final int EDIT_PROFILE_ERROR = 466;
    public static final int UNEXPECTED_ERROR = 499;

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
    public static final String GET_ALL_PARTICIPANTS = "/api/message/participants/";
    public static final String ACTIVATE_USER = "api/activate";
    public static final String RESET_PASSWORD = "api/password/{email}";
    public static final String GET_POSTPONE_EMAIL = "api/postpone";
}
