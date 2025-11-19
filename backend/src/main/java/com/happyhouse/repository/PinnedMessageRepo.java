package com.happyhouse.repository;

import com.happyhouse.model.PinnedMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository

// extends the database MongoBB to get the CRUD operations, works with PinnedMessage objects and ID is string
public interface PinnedMessageRepo extends MongoRepository<PinnedMessage, String> {

    // gets all pinned messages for the house while making sure the newest is presented first
    List<PinnedMessage> findByHouseIdOrderByPinnedAtDesc(String houseId);

    // checks if a message is already pinned if it is in our map
    Optional<PinnedMessage> findByMessageIdAndHouseId(String messageId, String houseId);
}
