package com.happyhouse.service;

import com.happyhouse.model.PinnedMessage;
import com.happyhouse.repository.PinnedMessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PinnedMessageService {

    // repo for the database operations like saving and unsaving pinned message
    private final PinnedMessageRepo pinnedMessageRepository;

    // get all pinned messages for a specific house
    public List<PinnedMessage> getPinnedMessagesByHouse(String houseId) {
        // method created in model file to find all of the pinned messages
        return pinnedMessageRepository.findByHouseIdOrderByPinnedAtDesc(houseId);
    }

    // how to pinn a new message
    public PinnedMessage pinMessage(String messageId, String houseId, String username,
                                    String content, String timestamp) {
        // this will check if message is already pinned, using the same method as above so we dont have dupe pinned messages
        var existing = pinnedMessageRepository.findByMessageIdAndHouseId(messageId, houseId);
        if (existing.isPresent()) {
            return existing.get();
        }

        // creates the new pinned message
        PinnedMessage pinnedMessage = new PinnedMessage();
        pinnedMessage.setMessageId(messageId);
        pinnedMessage.setHouseId(houseId);
        pinnedMessage.setUsername(username);
        pinnedMessage.setContent(content);
        pinnedMessage.setTimestamp(timestamp);

        return pinnedMessageRepository.save(pinnedMessage);
    }

    // method to unpin a message
    public void unpinMessage(String pinnedMessageId) {
        pinnedMessageRepository.deleteById(pinnedMessageId);
    }
}