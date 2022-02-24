package com.example.demo.repository;


import com.example.demo.model.Landlord;
import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LandlordRepository extends MongoRepository<Landlord, String> {

    Optional<Landlord> findByUser(User user);

}
