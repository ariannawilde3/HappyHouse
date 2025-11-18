package com.happyhouse.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "pinned_messages")
public class PinnedMessage {
    @Id
    private String id;

    // ID of the original message in chat
    private String messageId;

    // Which house this belongs to
    private String houseId;

    // Message details
    private String username;
    private String content;
    private String timestamp;

    // When it was pinned
    private LocalDateTime pinnedAt;

    public PinnedMessage() {
        this.pinnedAt = LocalDateTime.now();
    }
}