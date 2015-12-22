package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.util.EncryptionUtils;
import com.free.agent.util.ExtractFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.free.agent.model.User domainUser = userDao.findByEmail(email);
        domainUser.setLastActivity(new Date());
        userDao.update(domainUser);
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(domainUser.getEmail(), EncryptionUtils.decrypt(domainUser.getPassword()), enabled, //
                accountNonExpired, credentialsNonExpired, accountNonLocked, ExtractFunction.getAuthorities(domainUser.getRole()));
    }

}
