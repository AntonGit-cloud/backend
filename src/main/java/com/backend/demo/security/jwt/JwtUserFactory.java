package com.backend.demo.security.jwt;

import com.backend.demo.model.User;
import com.backend.demo.model.misc.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public class JwtUserFactory {

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles(), null),
                user.isActive()
        );
    }

    public static JwtUser create(User user, Role contextRole, String contextId) {
        return new JwtUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getRoles(), contextRole),
                contextId,
                user.isActive()
        );
    }

    private static Set<GrantedAuthority> mapToGrantedAuthorities(Set<Role> userRoles, Role role) {
        Set<GrantedAuthority> userAuthorities = userRoles.stream()
                .filter(userRole -> role == null || userRole.equals(role))
                .map(userRole -> new SimpleGrantedAuthority(userRole.name()))
                .collect(Collectors.toSet());

        if (userAuthorities.isEmpty()) {
            throw new AccessDeniedException("You can not access this source");
        }
        return userAuthorities;
    }
}
