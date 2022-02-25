package com.backend.demo.service.api;

import com.backend.demo.dto.AuthRequest;
import com.backend.demo.dto.AuthResponse;
import com.backend.demo.model.misc.Role;

import java.util.Set;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest, Set<Role> roles);

}

