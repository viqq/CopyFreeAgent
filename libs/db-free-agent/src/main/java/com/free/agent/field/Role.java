package com.free.agent.field;

/**
 * Created by antonPC on 21.06.15.
 */
public enum Role {
    ROLE_NOT_ACTIVATED, //postpone registration
    ROLE_NOT_CONFIRMED, // email doesn't confirm
    ROLE_MODERATOR, // simple user
    ROLE_ADMIN // admin
}
