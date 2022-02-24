package com.example.demo.service;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.misc.Role;
import com.example.demo.security.jwt.JwtTokenProvider;
import com.example.demo.security.jwt.JwtUser;
import com.example.demo.service.api.AuthService;
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
