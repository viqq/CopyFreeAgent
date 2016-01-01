package com.free.agent.service.impl;


import com.free.agent.FreeAgentAPI;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by antonPC on 16.08.15.
 */
@Service("loginFailureHandler")
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.getWriter().write("{\"error\":true,\"code\":" + getErrorMessage(e) + "}");
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();
    }

    private int getErrorMessage(AuthenticationException e) {
        if (e instanceof BadCredentialsException) {
            return FreeAgentAPI.LOGIN_ERROR;
        } else if (e instanceof AuthenticationServiceException) {
            return FreeAgentAPI.EMAIL_DID_NOT_REGISTERED_ERROR;
        } else {
            return FreeAgentAPI.UNEXPECTED_ERROR;
        }
    }
}
