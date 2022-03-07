package com.demo.backend.controller;

import com.demo.backend.dto.AuthRequest;
import com.demo.backend.dto.AuthResponse;
import com.demo.backend.model.misc.Role;
import com.demo.backend.service.api.AuthService;
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
