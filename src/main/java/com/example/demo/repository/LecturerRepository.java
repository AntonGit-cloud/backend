package com.example.demo.repository;


import com.example.demo.model.Lecturer;
import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LecturerRepository extends MongoRepository<Lecturer, String> {

    Optional<Lecturer> findByUser(User user);

}
