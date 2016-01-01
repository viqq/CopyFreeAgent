package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.dto.network.SocialProfile;
import com.free.agent.exception.EmailDidNotRegisteredException;
import com.free.agent.model.User;
import com.free.agent.util.ExtractFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by antonPC on 20.12.15.
 */
@Service("socialNetworkAuthenticationManager")
public class SocialNetworkAuthenticationManager implements AuthenticationManager {

    @Autowired
    private UserDao userDao;

    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public Authentication authenticate(Authentication auth) throws EmailDidNotRegisteredException, BadCredentialsException {
        String email = auth.getName();
        SocialProfile profile = (SocialProfile) auth.getCredentials();
        User user = userDao.findByEmail(email);
        if (user == null) {
            throw new EmailDidNotRegisteredException("Email " + email + "has not registered yet");
        }
        if (profile.isAuthentication(user)) {
            return new UsernamePasswordAuthenticationToken(auth.getName(),
                    auth.getCredentials(), ExtractFunction.getAuthorities(user.getRole()));
        }
        throw new BadCredentialsException("Bad Credentials");
    }
}


