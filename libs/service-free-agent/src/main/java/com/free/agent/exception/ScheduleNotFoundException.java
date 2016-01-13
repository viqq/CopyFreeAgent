package com.free.agent.exception;

/**
 * Created by antonPC on 13.01.16.
 */
public class ScheduleNotFoundException extends RuntimeException {

    public ScheduleNotFoundException() {
        super();
    }

    public ScheduleNotFoundException(String message) {
        super(message);
    }
}
