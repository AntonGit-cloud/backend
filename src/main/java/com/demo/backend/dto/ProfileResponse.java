package com.demo.backend.dto;

import com.demo.backend.model.misc.Role;
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
