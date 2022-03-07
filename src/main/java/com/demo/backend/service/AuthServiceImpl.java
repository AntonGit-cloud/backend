package com.demo.backend.service;

import com.demo.backend.dto.AuthRequest;
import com.demo.backend.dto.AuthResponse;
import com.demo.backend.model.misc.Role;
import com.demo.backend.security.jwt.JwtTokenProvider;
import com.demo.backend.security.jwt.JwtUser;
import com.demo.backend.service.api.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Set;

@AllArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(AuthRequest authRequest, Set<Role> roles) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        Role contextRole = roles.stream()
                .filter(userRole -> jwtUser.getAuthorities().stream().anyMatch(authority -> authority.getAuthority().equals(userRole.name())))
                .findFirst()
                .orElseThrow(() -> new AccessDeniedException("You can not access this source"));

        String token = jwtTokenProvider.createToken(jwtUser.getId(), authRequest.isRememberMe(), contextRole);
        return new AuthResponse(token);
    }
}
