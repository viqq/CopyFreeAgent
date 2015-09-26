package com.free.agent.service.impl;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.field.Role;
import com.free.agent.service.util.EncryptionUtils;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER, readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        com.free.agent.model.User domainUser = userDao.findByEmail(email);
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(domainUser.getEmail(), EncryptionUtils.decrypt(domainUser.getPassword()), enabled, accountNonExpired, //
                credentialsNonExpired, accountNonLocked, getAuthorities(domainUser.getRole()));
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return getGrantedAuthorities(getRoles(role));
    }

    public List<String> getRoles(Role role) {
        List<String> roles = Lists.newArrayList();
        if (role.equals(Role.ROLE_ADMIN)) {
            roles.add(Role.ROLE_MODERATOR.name());
            roles.add(Role.ROLE_ADMIN.name());
        } else if (role.equals(Role.ROLE_MODERATOR)) {
            roles.add(Role.ROLE_MODERATOR.name());
        }
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = Lists.newArrayList();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
