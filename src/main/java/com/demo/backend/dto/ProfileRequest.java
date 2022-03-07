package com.demo.backend.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@Data
public class ProfileRequest {
    @Valid
    @NotNull
    private PhoneNumberInfo phoneNumber;
    @NotBlank
    private String email;
    @NotNull
    private Locale language;
}

