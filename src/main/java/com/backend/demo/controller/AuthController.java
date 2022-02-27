package com.backend.demo.controller;

import com.backend.demo.dto.AuthRequest;
import com.backend.demo.dto.AuthResponse;
import com.backend.demo.model.misc.Role;
import com.backend.demo.service.api.AuthService;
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

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginStudent(@Valid @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest, Set.of(Role.ROLE_STUDENT, Role.ROLE_LECTURER)));
    }
}
