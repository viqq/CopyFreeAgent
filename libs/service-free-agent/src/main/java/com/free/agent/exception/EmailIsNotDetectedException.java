package com.free.agent.exception;

/**
 * Created by antonPC on 20.12.15.
 */
public class EmailIsNotDetectedException extends RuntimeException {

    public EmailIsNotDetectedException() {
        super();
    }

    public EmailIsNotDetectedException(String message) {
        super(message);
    }

}
