package com.happyhouse.repository;

import com.happyhouse.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByGoogleId(String googleId);
    
    Boolean existsByEmail(String email);
    
    Boolean existsByGoogleId(String googleId);
}
