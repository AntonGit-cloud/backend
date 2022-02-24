package com.example.demo.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class VerificationToken {
    private static final int DEFAULT_EXPIRATION = 172800; // 2 hours

    private String token;

    private LocalDateTime expiryDate;

    public VerificationToken() {
        this.token = UUID.randomUUID().toString();
        this.expiryDate = LocalDateTime.now().plusSeconds(DEFAULT_EXPIRATION);
    }

    public VerificationToken(int expirationDays) {
        this.token = UUID.randomUUID().toString();
        this.expiryDate = LocalDateTime.now().plusDays(expirationDays);
    }
}
