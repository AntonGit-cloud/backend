package com.example.demo.controller;

import com.example.demo.dto.ProfileRequest;
import com.example.demo.dto.ProfileResponse;
import com.example.demo.service.api.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "User")
@AllArgsConstructor
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_TENANT') or hasRole('ROLE_LANDLORD') or hasRole('ROLE_ACCOUNT_ADMINISTRATOR') or hasRole('ROLE_ACCOUNT_MANAGER')")
    @GetMapping("profile")
    public ResponseEntity<ProfileResponse> getCurrentUserProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }


    @PreAuthorize("hasRole('ROLE_TENANT') or hasRole('ROLE_LANDLORD') or hasRole('ROLE_ACCOUNT_ADMINISTRATOR') or hasRole('ROLE_ACCOUNT_MANAGER')")
    @PutMapping("profile")
    public ResponseEntity<ProfileResponse> updateProfile(@Valid @RequestBody ProfileRequest profileRequest) {
        return ResponseEntity.ok(userService.updateCurrentUserProfile(profileRequest));
    }

/*

    @PreAuthorize("hasRole('ROLE_TENANT') or hasRole('ROLE_LANDLORD') or hasRole('ROLE_ACCOUNT_ADMINISTRATOR') or hasRole('ROLE_ACCOUNT_MANAGER')")
    @PatchMapping("change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody PasswordSetRequest passwordSetRequest) {
        userService.changePassword(passwordSetRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        userService.resetPassword(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("confirm-reset-password")
    public ResponseEntity<?> confirmPasswordReset(@Valid @RequestBody PasswordSetRequest passwordSetRequest) {
        userService.confirmPasswordReset(passwordSetRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("confirm-account")
    public ResponseEntity<?> confirmAccount(@Valid @RequestBody PasswordSetRequest passwordSetRequest) {
        userService.confirmAccount(passwordSetRequest);
        return ResponseEntity.ok().build();
    }*/

}
