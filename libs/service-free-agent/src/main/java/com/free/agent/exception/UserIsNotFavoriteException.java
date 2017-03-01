package com.free.agent.exception;

/**
 * Created by antonPC on 14.12.15.
 */
public class UserIsNotFavoriteException extends RuntimeException {

    public UserIsNotFavoriteException() {
        super();
    }

    public UserIsNotFavoriteException(String message) {
        super(message);
    }

}
