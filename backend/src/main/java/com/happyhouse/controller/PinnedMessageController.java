package com.happyhouse.controller;

import com.happyhouse.model.PinnedMessage;
import com.happyhouse.model.User;
import com.happyhouse.service.AuthService;
import com.happyhouse.service.PinnedMessageService;
import com.happyhouse.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PinnedMessageController {

    private final PinnedMessageService pinnedMessageService;
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // GET all pinned messages for user's house
    @GetMapping("/pinned")
    public ResponseEntity<List<PinnedMessage>> getPinnedMessages(
            @RequestHeader("Authorization") String token) {
        // Extract JWT token
        String jwt = token.substring(7);
        String email = jwtUtil.extractEmail(jwt);
        User user = authService.findByEmail(email);

        // Get pinned messages for this user's house
        List<PinnedMessage> pinnedMessages = pinnedMessageService.getPinnedMessagesByHouse(user.getHouseId());
        return ResponseEntity.ok(pinnedMessages);
    }

    // POST to pin a new message
    @PostMapping("/pin")
    public ResponseEntity<PinnedMessage> pinMessage(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> request) {
        // Extract JWT token
        String jwt = token.substring(7);
        String email = jwtUtil.extractEmail(jwt);
        User user = authService.findByEmail(email);

        // Get message details from request
        String messageId = request.get("messageId");
        String username = request.get("username");
        String content = request.get("content");
        String timestamp = request.get("timestamp");

        // Pin the message
        PinnedMessage pinnedMessage = pinnedMessageService.pinMessage(
                messageId, user.getHouseId(), username, content, timestamp
        );

        return ResponseEntity.ok(pinnedMessage);
    }

    // DELETE to unpin a message
    @DeleteMapping("/unpin/{pinnedMessageId}")
    public ResponseEntity<Void> unpinMessage(
            @PathVariable String pinnedMessageId,
            @RequestHeader("Authorization") String token) {
        // Unpin the message
        pinnedMessageService.unpinMessage(pinnedMessageId);
        return ResponseEntity.ok().build();
    }
}