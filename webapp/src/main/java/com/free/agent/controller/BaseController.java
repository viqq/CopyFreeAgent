package com.free.agent.controller;

import com.free.agent.FreeAgentAPI;
import com.free.agent.Response;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * Created by antonPC on 23.06.15.
 */
@ControllerAdvice
public class BaseController {
    private static final Logger LOGGER = Logger.getLogger(BaseController.class);
    public static final String PRODUCES = MediaType.APPLICATION_JSON_VALUE + "; charset=UTF-8";

    //@ExceptionHandler(Exception.class)
    public String exceptionHandler() {
        LOGGER.error("Exception!!! Catch in BaseController");
        return Response.error(FreeAgentAPI.UNEXPECTED_ERROR);
    }
}
