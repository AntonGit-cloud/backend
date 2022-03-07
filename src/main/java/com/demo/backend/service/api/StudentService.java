package com.demo.backend.service.api;

import com.demo.backend.model.Student;
import com.demo.backend.model.User;

import java.util.Optional;

public interface StudentService {
    Student getById(String id);

    Student getCurrentUserTenant();

    Optional<Student> getByUser(User user);

    Student save(Student tenant);

}
