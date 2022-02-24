package com.example.demo.service;

import com.example.demo.model.Landlord;
import com.example.demo.model.Tenant;
import com.example.demo.model.User;
import com.example.demo.model.misc.Role;
import com.example.demo.repository.LandlordRepository;
import com.example.demo.repository.TenantRepository;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Slf4j
@Service
public class FixtureService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LandlordRepository landlordRepository;

    @Autowired
    private TenantRepository tenantRepository;
    
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

        User tenantUser1 = createUser("613f858ed9fceec4d1f6f47b", "Anton@Afanasiev.com",
                "+49", "661234561", "Anton", "Afanasiev", "DG7Uj6xp26FjFVyW", Set.of(Role.ROLE_TENANT));
        User tenantUser2 = createUser("613f858ed9fceec4d1f6f47c", "Ilya@Marinichev.com",
                "+49", "625712349", "Ilya", "Marinichev", "cVHdJL4hGZ4yMGZD", Set.of(Role.ROLE_TENANT));

        User landlordUser1 = createUser("613f85f7da7e9127cd0d9725", "landlord1@garentii.com",
                "+49", "661234562", "Jonathan", "Fischer", "5UWXq5urus5Vz6BR", Set.of(Role.ROLE_LANDLORD));
        User landlordUser2 = createUser("613f85f7da7e9127cd0d9726", "landlord2@garentii.com",
                "+49", "661234563", "Luis", "Schneider", "mBazA7NLuEPHcf4A", Set.of(Role.ROLE_LANDLORD));
        User landlordUser3 = createUser("613f85f7da7e9127cd0d9727", "landlord3@garentii.com",
                "+49", "954539108", "Elias", "Schmidt", "6j54SPTh7LJBTjsN", Set.of(Role.ROLE_LANDLORD));
        User landlordUser4 = createUser("613f85f7da7e9127cd0d9728", "landlord4@garentii.com",
                "+49", "508583695", "Finn", "Müller", "b8MVd5xkhMhtETzc", Set.of(Role.ROLE_LANDLORD));

        Landlord landlord1 = createLandlord("613f861f8b1317633733bda1", landlordUser1);
        Landlord landlord2 = createLandlord("613f861f8b1317633733bda2", landlordUser2);
        Landlord landlord3 = createLandlord("613f861f8b1317633733bda3", landlordUser3);
        Landlord landlord4 = createLandlord("613f861f8b1317633733bda4", landlordUser4);

        Tenant tenant1 = createTenant(tenantUser1, "613f86a2e4c4273919ae3eac");
        Tenant tenant2 = createTenant(tenantUser2, "613f86a2e4c4273919ae3ead");
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

    private Tenant createTenant(User user, String id) {
        return tenantRepository.findById(id)
                .orElseGet(() -> {
                    Tenant tenant = new Tenant();
                    tenant.setId(id);
                    tenant.setUser(user);
                    tenant.setCreatedAt(LocalDateTime.now());


                    Tenant savedTenant = tenantRepository.save(tenant);
                    log.info("Fixture tenant with id '{}' successfully created", savedTenant.getId());
                    return savedTenant;
                });
    }

    private Landlord createLandlord(String landlordId, User user) {
        return landlordRepository.findById(landlordId)
                .orElseGet(() -> {
                    Landlord landlord = new Landlord();
                    landlord.setId(landlordId);
                    landlord.setUser(user);
                    landlord.setCreatedAt(LocalDateTime.now());


                    Landlord savedLandlord = landlordRepository.save(landlord);
                    log.info("Fixture landlord with id '{}' successfully created", savedLandlord.getId());
                    return savedLandlord;
                });
    }

}
