package com.backend.demo.repository;

import com.backend.demo.model.Student;
import com.backend.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {

    Optional<Student> findByUser(User user);

    @Query("{'refIds.?0': ?1 }")
    Optional<Student> findByRefId(String lecturerId, String tenantRefId);


}
