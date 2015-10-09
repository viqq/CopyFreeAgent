package com.free.agent.service;

/**
 * Created by antonPC on 09.10.15.
 */
public class WrongLinkException extends RuntimeException {

    public WrongLinkException() {
        super();
    }

    public WrongLinkException(String message) {
        super(message);
    }
}
