package com.demo.backend.service.api;

import com.demo.backend.dto.AuthRequest;
import com.demo.backend.dto.AuthResponse;
import com.demo.backend.model.misc.Role;

import java.util.Set;

public interface AuthService {

    AuthResponse login(AuthRequest authRequest, Set<Role> roles);

}

