package com.backend.demo.service.api;

import com.backend.demo.model.Lecturer;
import com.backend.demo.model.User;

import java.util.Optional;


public interface LecturerService {

    Lecturer getById(String id);

    Lecturer getCurrentUserLecturer();

    Optional<Lecturer> getByUser(User user);

    Lecturer save(Lecturer lecturer);

}
