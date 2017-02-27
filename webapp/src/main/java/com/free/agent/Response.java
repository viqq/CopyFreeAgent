package com.free.agent;

import com.google.gson.Gson;
import lombok.Getter;

/**
 * Created by antonPC on 28.07.15.
 */
@Getter
public class Response<T> {
    private final T payload;
    private final Boolean error;
    private final Integer[] status;

    private Response(T payload) {
        this.payload = payload;
        this.error = false;
        this.status = null;
    }

    private Response(T payload, Boolean error) {
        this.payload = payload;
        this.error = error;
        this.status = null;
    }

    private Response(Integer... status) {
        this.payload = null;
        this.error = true;
        this.status = status;
    }

    public static String ok() {
        return ok(FreeAgentAPI.OK);
    }

    public static <T> String ok(T payload) {
        return new Response<>(payload).toJSON();
    }

    public static String error(Integer status) {
        return new Response<>(status, true).toJSON();
    }

    public static String error(Integer... status) {
        return new Response<>(status).toJSON();
    }

    private String toJSON() {
        return new Gson().toJson(this);
    }
}