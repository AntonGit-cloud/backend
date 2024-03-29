package com.demo.backend.service;

import com.demo.backend.model.EducationalInstitution;
import com.demo.backend.model.Lecturer;
import com.demo.backend.model.Student;
import com.demo.backend.model.User;
import com.demo.backend.model.misc.Role;
import com.demo.backend.repository.InstitutionRepository;
import com.demo.backend.repository.LecturerRepository;
import com.demo.backend.repository.StudentRepository;
import com.demo.backend.repository.UserRepository;
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
    private LecturerRepository lecturerRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private InstitutionRepository institutionRepository;

    @Value("${fixture.bootstrap.enable:false}")
    private Boolean bootstrapEnabled;


    @PostConstruct
    public void run() {
        if (bootstrapEnabled) {
            createAll();
        }
    }

    private void createAll() {

        EducationalInstitution educationalInstitution = createInstitution("6134d1f6f47b", "DPI");

        User studentUser1 = createUser("613f858ed9fceec4d1f6f47b", "Anton@Afanasiev.com",
                "+49", "661234561", "Anton", "Afanasiev", "DG7Uj6xp26FjFVyW", Set.of(Role.ROLE_STUDENT),
                educationalInstitution);
        User studentUser2 = createUser("613f858ed9fceec4d1f6f47c", "Ilya@Marinichev.com",
                "+49", "625712349", "Ilya", "Marinichev", "cVHdJL4hGZ4yMGZD", Set.of(Role.ROLE_STUDENT),
                educationalInstitution);

        User lecturerUser1 = createUser("613f85f7da7e9127cd0d9725", "lecturer1@diplom.com",
                "+49", "661234562", "Jonathan", "Fischer", "5UWXq5urus5Vz6BR", Set.of(Role.ROLE_LECTURER),
                educationalInstitution);
        User lecturerUser2 = createUser("613f85f7da7e9127cd0d9726", "lecturer2@diplom.com",
                "+49", "661234563", "Luis", "Schneider", "mBazA7NLuEPHcf4A", Set.of(Role.ROLE_LECTURER),
                educationalInstitution);
        User lecturerUser3 = createUser("613f85f7da7e9127cd0d9727", "lecturer3@diplom.com",
                "+49", "954539108", "Elias", "Schmidt", "6j54SPTh7LJBTjsN", Set.of(Role.ROLE_LECTURER),
                educationalInstitution);
        User lecturerUser4 = createUser("613f85f7da7e9127cd0d9728", "lecturer4@diplom.com",
                "+49", "508583695", "Finn", "Müller", "b8MVd5xkhMhtETzc", Set.of(Role.ROLE_LECTURER),
                educationalInstitution);

        Lecturer lecturer1 = createLecturer("613f861f8b1317633733bda1", lecturerUser1);
        Lecturer lecturer2 = createLecturer("613f861f8b1317633733bda2", lecturerUser2);
        Lecturer lecturer3 = createLecturer("613f861f8b1317633733bda3", lecturerUser3);
        Lecturer lecturer4 = createLecturer("613f861f8b1317633733bda4", lecturerUser4);

        Student student = createStudent(studentUser1, "613f86a2e4c4273919ae3eac");
        Student student2 = createStudent(studentUser2, "613f86a2e4c4273919ae3ead");
    }

    private User createUser(String id,
                            String email,
                            String dialCode,
                            String phoneNumber,
                            String firstName,
                            String lastName,
                            String password,
                            Set<Role> roles,
                            EducationalInstitution educationalInstitution) {
        return userRepository.findById(id)
                .orElseGet(() -> {
                    User user = new User();
                    user.setId(id);
                    user.setEmail(email);
                    user.setPassword(passwordEncoder.encode(password));
                    user.setActive(true);
                    user.setFirstName(firstName);
                    user.setEducationalInstitution(educationalInstitution);
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

    private Student createStudent(User user, String id) {
        return studentRepository.findById(id)
                .orElseGet(() -> {
                    Student tenant = new Student();
                    tenant.setId(id);
                    tenant.setUser(user);
                    tenant.setCreatedAt(LocalDateTime.now());


                    Student savedTenant = studentRepository.save(tenant);
                    log.info("Fixture tenant with id '{}' successfully created", savedTenant.getId());
                    return savedTenant;
                });
    }

    private Lecturer createLecturer(String lecturerId, User user) {
        return lecturerRepository.findById(lecturerId)
                .orElseGet(() -> {
                    Lecturer lecturer = new Lecturer();
                    lecturer.setId(lecturerId);
                    lecturer.setUser(user);
                    lecturer.setCreatedAt(LocalDateTime.now());


                    Lecturer savedLecturer = lecturerRepository.save(lecturer);
                    log.info("Fixture lecturer with id '{}' successfully created", savedLecturer.getId());
                    return savedLecturer;
                });
    }

    private EducationalInstitution createInstitution(String Id, String name) {

        return institutionRepository.findById(Id)
                .orElseGet(() -> {
                    EducationalInstitution educationalInstitution = new EducationalInstitution();
                    educationalInstitution.setId(Id);
                    educationalInstitution.setName(name);
                    // lecturer.setCreatedAt(LocalDateTime.now());


                    EducationalInstitution savedInstitution = institutionRepository.save(educationalInstitution);
                    log.info("Fixture lecturer with id '{}' successfully created", savedInstitution.getId());
                    return savedInstitution;
                });
    }

}
