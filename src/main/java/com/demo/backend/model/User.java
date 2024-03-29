package com.demo.backend.model;

import com.demo.backend.model.misc.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Document("user")
public class User {
    @Id
    private String id;

    @Indexed(name = "email_key", unique = true)
    private String email;

    private String firstName;

    private String lastName;

    @DBRef
    private EducationalInstitution educationalInstitution;

    private String dialCode;

    private LocalDate birthDate;

    private String phoneNumber;

    private String password;

    private VerificationToken accountConfirmationToken;

    private VerificationToken passwordResetToken;

    private Set<Role> roles = new HashSet<>();

    private boolean active;

}
