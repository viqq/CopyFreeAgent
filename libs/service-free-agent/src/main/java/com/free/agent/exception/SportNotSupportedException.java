package com.free.agent.exception;

/**
 * Created by antonPC on 16.01.16.
 */
public class SportNotSupportedException extends RuntimeException {

    public SportNotSupportedException() {
        super();
    }

    public SportNotSupportedException(String message) {
        super(message);
    }
}
