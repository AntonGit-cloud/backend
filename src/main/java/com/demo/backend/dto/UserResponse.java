package com.demo.backend.dto;

import com.demo.backend.model.misc.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private PhoneNumberInfo phone;
    private Role role;
    private Locale language;
}
