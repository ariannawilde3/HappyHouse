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

// allows REST Api Controller
@RestController
// all endpoints will now start with api/messages
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")


public class PinnedMessageController {

    // this is the service for pinning a message aka buis logic
    private final PinnedMessageService pinnedMessageService;
    // auth service
    private final AuthService authService;
    // token operations
    private final JwtUtil jwtUtil;

    // get all the pinned messages and maps the get request to /api/messages/pinned
    @GetMapping("/pinned")

    public ResponseEntity<List<PinnedMessage>> getPinnedMessages(

            // get the token
            @RequestHeader("Authorization") String token) {
        // takes out the different tokens
        String jwt = token.substring(7);
        String email = jwtUtil.extractEmail(jwt);
        User user = authService.findByEmail(email);

        // ge tth epinned messages for this users house
        List<PinnedMessage> pinnedMessages = pinnedMessageService.getPinnedMessagesByHouse(user.getHouseId());
        return ResponseEntity.ok(pinnedMessages);
    }

    // how to pin a new message
    @PostMapping("/pin")
    public ResponseEntity<PinnedMessage> pinMessage(
            @RequestHeader("Authorization") String token,
            @RequestBody Map<String, String> request) {
        // same token process as above
        String jwt = token.substring(7);
        String email = jwtUtil.extractEmail(jwt);
        User user = authService.findByEmail(email);

        // get all the message details needed
        String messageId = request.get("messageId");
        String username = request.get("username");
        String content = request.get("content");
        String timestamp = request.get("timestamp");

        // actually pin the message
        PinnedMessage pinnedMessage = pinnedMessageService.pinMessage(
                messageId, user.getHouseId(), username, content, timestamp
        );

        return ResponseEntity.ok(pinnedMessage);
    }

    // how to delete or unpin a messahe
    @DeleteMapping("/unpin/{pinnedMessageId}")
    public ResponseEntity<Void> unpinMessage(
            @PathVariable String pinnedMessageId,
            @RequestHeader("Authorization") String token) {
        // actually unpin the message 
        pinnedMessageService.unpinMessage(pinnedMessageId);
        return ResponseEntity.ok().build();
    }
}