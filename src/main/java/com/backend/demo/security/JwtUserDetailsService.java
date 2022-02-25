package com.backend.demo.security;


import com.backend.demo.service.api.LecturerService;
import com.backend.demo.model.Lecturer;
import com.backend.demo.model.Student;
import com.backend.demo.model.User;
import com.backend.demo.model.misc.Role;
import com.backend.demo.security.jwt.JwtUserFactory;
import com.backend.demo.service.api.StudentService;
import com.backend.demo.service.api.UserService;
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

    private final StudentService studentService;

    private final LecturerService lecturerService;

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
            case ROLE_STUDENT:
                return studentService.getByUser(user)
                        .map(Student::getId)
                        .orElseThrow(() -> new AccessDeniedException("Can not access this source"));
            case ROLE_LECTURER:
                return lecturerService.getByUser(user)
                        .map(Lecturer::getId)
                        .orElseThrow(() -> new AccessDeniedException("Can not access this source"));
            case ROLE_SYSTEM:
                return user.getId();
            case ROLE_ACCOUNT_MANAGER:
            case ROLE_ACCOUNT_ADMINISTRATOR:
            default:
                throw new AccessDeniedException("Can not access this source");
        }

    }
}
