package com.example.demo.service.api;

import com.example.demo.model.Lecturer;
import com.example.demo.model.User;

import java.util.Optional;


public interface LecturerService {

    Lecturer getById(String id);

    Lecturer getCurrentUserLecturer();

    Optional<Lecturer> getByUser(User user);

    Lecturer save(Lecturer lecturer);

}
