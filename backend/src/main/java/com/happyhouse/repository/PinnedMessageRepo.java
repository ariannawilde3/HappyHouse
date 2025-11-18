package com.happyhouse.repository;

import com.happyhouse.model.PinnedMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PinnedMessageRepo extends MongoRepository<PinnedMessage, String> {

    // Get all pinned messages for a house, newest first
    List<PinnedMessage> findByHouseIdOrderByPinnedAtDesc(String houseId);

    // Check if a message is already pinned
    Optional<PinnedMessage> findByMessageIdAndHouseId(String messageId, String houseId);

    // Delete a specific pinned message
    void deleteByMessageIdAndHouseId(String messageId, String houseId);
}
