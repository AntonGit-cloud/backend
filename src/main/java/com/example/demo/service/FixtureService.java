package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.misc.Role;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Set;

@Slf4j
@Service
public class FixtureService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Value("${fixture.bootstrap.enable:false}")
    private Boolean bootstrapEnabled;


    @PostConstruct
    public void run() {
        if (bootstrapEnabled) {
            createAll();
        }
    }

    private void createAll() {

    }

    private User createUser(String id,
                            String email,
                            String dialCode,
                            String phoneNumber,
                            String firstName,
                            String lastName,
                            String password,
                            Set<Role> roles) {
        return userRepository.findById(id)
                .orElseGet(() -> {
                    User user = new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setPassword(passwordEncoder.encode(password));
                    user.setActive(true);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setRoles(roles);
                    user.setDialCode(dialCode);
                    user.setPhoneNumber(phoneNumber);
                    user.setBirthDate(LocalDate.now());

                    User savedUser = userRepository.save(user);
                    log.info("Fixture user with email '{}' successfully created", savedUser.getEmail());
                    return savedUser;
                });
    }

}
