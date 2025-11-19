package com.happyhouse.repository;

import com.happyhouse.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Repositories allow the backend to interact with the database
// this connects to our mongo db and allows us to use the stored data and filter it
public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByEmail(String email);
    
    Optional<User> findByGoogleId(String googleId);

    Optional<User> findById(int id);
    
    Boolean existsByEmail(String email);
    
    Boolean existsByGoogleId(String googleId);
}
