package com.example.demo.service.api;

import com.example.demo.model.Student;
import com.example.demo.model.User;

import java.util.Optional;

public interface StudentService {
    Student getById(String id);

    Student getCurrentUserTenant();

    Optional<Student> getByUser(User user);

    Student save(Student tenant);

}
