package com.free.agent.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by antonPC on 21.12.15.
 */
public class EmailDidNotRegisteredException extends AuthenticationException {

    public EmailDidNotRegisteredException() {
        super("Email didn't register");
    }

    public EmailDidNotRegisteredException(String message) {
        super(message);
    }
}
