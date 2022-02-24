package com.example.demo.service.api;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.misc.Role;

import java.util.Set;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest, Set<Role> roles);

}

