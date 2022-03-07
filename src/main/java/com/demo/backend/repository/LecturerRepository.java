package com.demo.backend.repository;


import com.demo.backend.model.Lecturer;
import com.demo.backend.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository extends MongoRepository<Lecturer, String> {

    Optional<Lecturer> findByUser(User user);

}
