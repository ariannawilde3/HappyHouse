package com.happyhouse.repository;

import com.happyhouse.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// Repositories allow the backend to interact with the database
// this connects to our mongo db and allows us to use the stored data and filter it
public interface ChatRepository extends MongoRepository<Chat, String> {

    Optional<Chat> findByChatID(String chatID);
}