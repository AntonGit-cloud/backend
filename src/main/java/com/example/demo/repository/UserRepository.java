package com.example.demo.repository;

import com.example.demo.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmailIgnoreCase(String email);

    @Query("{$or: [{ email: {$regex : '^?0$', $options: 'i'} }, { $expr: { $eq: [ ?1, { $concat: [\"$dialCode\", \"$phoneNumber\"]}]}}]}")
    Optional<User> findByEmailOrPhone(String email, String phone);

    Optional<User> findByDialCodeAndPhoneNumber(String dialCode, String phoneNumber);

    Optional<User> findByEmailIgnoreCaseOrDialCodeAndPhoneNumber(String email, String dialCode, String phoneNumber);

    Optional<User> findByPasswordResetToken_Token(String token);

    Optional<User> findByAccountConfirmationToken_Token(String token);

}