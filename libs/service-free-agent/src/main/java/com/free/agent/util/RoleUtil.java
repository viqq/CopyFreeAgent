package com.free.agent.util;

import com.free.agent.field.Role;
import com.google.common.collect.Lists;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by antonPC on 19.08.15.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RoleUtil {

    public static Collection<? extends GrantedAuthority> getAuthorities(Role role) {
        return getRoles(role).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private static List<String> getRoles(Role role) {
        List<String> roles = Lists.newArrayList();
        switch (role) {
            case ROLE_ADMIN: {
                roles.add(Role.ROLE_ADMIN.name());
                roles.add(Role.ROLE_MODERATOR.name());
                roles.add(Role.ROLE_NOT_CONFIRMED.name());
                roles.add(Role.ROLE_NOT_ACTIVATED.name());
                return roles;
            }
            case ROLE_MODERATOR: {
                roles.add(Role.ROLE_MODERATOR.name());
                roles.add(Role.ROLE_NOT_CONFIRMED.name());
                roles.add(Role.ROLE_NOT_ACTIVATED.name());
                return roles;
            }
            case ROLE_NOT_CONFIRMED: {
                roles.add(Role.ROLE_NOT_CONFIRMED.name());
                roles.add(Role.ROLE_NOT_ACTIVATED.name());
                return roles;
            }
            default: {
                roles.add(Role.ROLE_NOT_ACTIVATED.name());
                return roles;
            }
        }
    }

}
