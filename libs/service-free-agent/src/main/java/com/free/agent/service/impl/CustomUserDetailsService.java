package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.exception.EmailDidNotRegisteredException;
import com.free.agent.util.EncryptionUtils;
import com.free.agent.util.FunctionUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.free.agent.model.User domainUser = userDao.findByEmail(email);
        if (domainUser == null) {
            throw new EmailDidNotRegisteredException(email + " didn't register yet");
        }
        domainUser.setLastActivity(DateTime.now().toDate());
        userDao.update(domainUser);

        return new User(domainUser.getEmail(), EncryptionUtils.decrypt(domainUser.getPassword()),
                FunctionUtils.getAuthorities(domainUser.getRole()));
    }

}
