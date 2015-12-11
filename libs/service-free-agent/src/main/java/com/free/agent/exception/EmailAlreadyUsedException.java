package com.free.agent.exception;

/**
 * Created by antonPC on 01.11.15.
 */
public class EmailAlreadyUsedException extends RuntimeException {

    public EmailAlreadyUsedException() {
        super();
    }

    public EmailAlreadyUsedException(String message) {
        super(message);
    }

}
