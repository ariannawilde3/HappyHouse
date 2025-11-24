package com.happyhouse.controller;

import java.util.ArrayList;
import java.util.List;

import com.happyhouse.model.Chat;
import com.happyhouse.model.User;
import com.happyhouse.model.Message;
import com.happyhouse.service.ChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chats")
@CrossOrigin(origins = "*")
public class ChatController{

    private final ChatService chatService;

    //view messages
    @GetMapping("/{chatID}/messages")
    public ResponseEntity<List<Message>> getMessages(@PathVariable String chatID) {
        return ResponseEntity.ok(chatService.getMessages(chatID));
    }

    @PostMapping("/{chatID}/messages")
    public ResponseEntity<Message> sendMessage(@PathVariable String chatID,
                                               @RequestBody SendMessageRequest request) {
        // In real app, validate/authenticate sender here
        Message sentMessage = chatService.sendMessage(chatID, request.getSender(), request.getContent());
        return ResponseEntity.ok(sentMessage);
    }

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/")
    public Chat createChat(String chatID, String chatName, List<User> members, List<Message> messages) {
        //Chat chat = new Chat(chatID, chatName, members, messages);
        Chat chat = new Chat("1", "Test Chat", new ArrayList<>(), new ArrayList<>());
        chatService.saveChat(chat);  // <- this writes it to the database
        return chat;
    }

    public static class SendMessageRequest {
        private User sender;
        private String content;

        // Getters/setters
        public User getSender() {
            return sender;
        }

        public void setSender(User sender) {
            this.sender = sender;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}