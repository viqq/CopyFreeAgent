package com.free.agent;

import com.google.gson.Gson;

/**
 * Created by antonPC on 28.07.15.
 */
public final class Response<T> {
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

    public T getPayload() {
        return payload;
    }

    public Boolean getError() {
        return error;
    }

    public Integer[] getStatus() {
        return status;
    }

    private String toJSON() {
        return new Gson().toJson(this);
    }
}