package com.example.demo.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class PhoneNumberInfo {
    @Pattern(regexp = "^\\+?[0-9]{1,4}$")
    private String dialCode;
    @Pattern(regexp = "^[0-9]{5,13}$")
    private String number;
}
