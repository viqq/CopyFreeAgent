package com.free.agent.service;

import com.free.agent.config.FreeAgentConstant;
import com.free.agent.dao.UserDao;
import com.free.agent.role.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDao userDAO;

    @Transactional(value = FreeAgentConstant.TRANSACTION_MANAGER)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        com.free.agent.model.User domainUser = userDAO.findByLogin(login);
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new User(domainUser.getLogin(), domainUser.getPassword(), enabled, accountNonExpired, credentialsNonExpired, accountNonLocked,
                getAuthorities(domainUser.getRole())
        );
    }

    public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    public List<String> getRoles(Role role) {

        List<String> roles = new ArrayList<String>();

        if (role.equals(Role.ROLE_ADMIN)) {
            roles.add("ROLE_MODERATOR");
            roles.add("ROLE_ADMIN");
        } else if (role.equals(Role.ROLE_MODERATOR)) {
            roles.add("ROLE_MODERATOR");
        }
        return roles;
    }

    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

}
