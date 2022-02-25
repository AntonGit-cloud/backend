package com.backend.demo.service.api;

import com.backend.demo.model.Student;
import com.backend.demo.model.User;

import java.util.Optional;

public interface StudentService {
    Student getById(String id);

    Student getCurrentUserTenant();

    Optional<Student> getByUser(User user);

    Student save(Student tenant);

}
