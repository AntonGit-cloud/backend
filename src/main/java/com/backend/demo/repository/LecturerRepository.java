package com.backend.demo.repository;


import com.backend.demo.model.Lecturer;
import com.backend.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository extends MongoRepository<Lecturer, String> {

    Optional<Lecturer> findByUser(User user);

}
