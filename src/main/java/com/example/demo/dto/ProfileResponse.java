package com.example.demo.dto;

import com.example.demo.model.misc.Role;
import lombok.Data;

@Data
public class ProfileResponse {

    private String id;
    private String name;
    //private String address;
    private String email;
    private PhoneNumberInfo phoneNumber;

    private Role role;
    //private Locale language;

}
