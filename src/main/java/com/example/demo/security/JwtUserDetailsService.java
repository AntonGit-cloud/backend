package com.example.demo.security;


import com.example.demo.model.User;
import com.example.demo.model.misc.Role;
import com.example.demo.security.jwt.JwtUserFactory;
import com.example.demo.service.api.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User existingUser = userService.getByEmailOrPhone(login);
        return JwtUserFactory.create(existingUser);
    }

    public UserDetails loadById(String userId, Role role) throws UsernameNotFoundException {
        User user = userService.getById(userId);
        String contextId = getContextId(user, role);
        return JwtUserFactory.create(user, role, contextId);
    }

    private String getContextId(User user, Role role) {
        switch (role) {
            case ROLE_TENANT:
            case ROLE_LANDLORD:

            case ROLE_SYSTEM:
                return user.getId();
            case ROLE_ACCOUNT_MANAGER:
            case ROLE_ACCOUNT_ADMINISTRATOR:
            default:
                throw new AccessDeniedException("Can not access this source");
        }

    }
}
