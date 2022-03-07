package com.demo.backend.service.api;

import com.demo.backend.model.Lecturer;
import com.demo.backend.model.User;

import java.util.Optional;


public interface LecturerService {

    Lecturer getById(String id);

    Lecturer getCurrentUserLecturer();

    Optional<Lecturer> getByUser(User user);

    Lecturer save(Lecturer lecturer);

}
