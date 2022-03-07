package com.demo.backend.dto;

import com.demo.backend.validation.EqualFields;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@EqualFields(baseField = "password", matchField = "reEnteredPassword")
public class PasswordSetRequest {

    @NotBlank
    private String password;

    @NotBlank
    private String reEnteredPassword;

    private String token;
}
