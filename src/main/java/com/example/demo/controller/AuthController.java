package com.example.demo.controller;

import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.example.demo.model.misc.Role;
import com.example.demo.service.api.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Set;

@Tag(name = "Auth")
@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("tenant/login")
    public ResponseEntity<AuthResponse> loginTenant(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest, Set.of(Role.ROLE_TENANT)));
    }

    @PostMapping("landlord/login")
    public ResponseEntity<AuthResponse> loginLandlord(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest, Set.of(Role.ROLE_LANDLORD, Role.ROLE_ACCOUNT_MANAGER, Role.ROLE_ACCOUNT_ADMINISTRATOR)));
    }
}
