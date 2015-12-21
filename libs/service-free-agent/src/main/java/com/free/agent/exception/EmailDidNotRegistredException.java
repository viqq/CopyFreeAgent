package com.free.agent.exception;

/**
 * Created by antonPC on 21.12.15.
 */
public class EmailDidNotRegistredException extends RuntimeException {

    public EmailDidNotRegistredException() {
        super();
    }

    public EmailDidNotRegistredException(String message) {
        super(message);
    }
}
