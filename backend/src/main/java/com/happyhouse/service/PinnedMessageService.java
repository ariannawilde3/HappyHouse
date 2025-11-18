package com.happyhouse.service;

import com.happyhouse.model.PinnedMessage;
import com.happyhouse.repository.PinnedMessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PinnedMessageService {

    private final PinnedMessageRepo pinnedMessageRepository;

    // Get all pinned messages for a specific house
    public List<PinnedMessage> getPinnedMessagesByHouse(String houseId) {
        return pinnedMessageRepository.findByHouseIdOrderByPinnedAtDesc(houseId);
    }

    // Pin a new message
    public PinnedMessage pinMessage(String messageId, String houseId, String username,
                                    String content, String timestamp) {
        // Check if message is already pinned
        var existing = pinnedMessageRepository.findByMessageIdAndHouseId(messageId, houseId);
        if (existing.isPresent()) {
            return existing.get();
        }

        // Create new pinned message
        PinnedMessage pinnedMessage = new PinnedMessage();
        pinnedMessage.setMessageId(messageId);
        pinnedMessage.setHouseId(houseId);
        pinnedMessage.setUsername(username);
        pinnedMessage.setContent(content);
        pinnedMessage.setTimestamp(timestamp);

        return pinnedMessageRepository.save(pinnedMessage);
    }

    // Unpin a message
    public void unpinMessage(String pinnedMessageId) {
        pinnedMessageRepository.deleteById(pinnedMessageId);
    }
}