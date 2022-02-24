package com.example.demo.dto;

import com.example.demo.validation.EqualFields;
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
