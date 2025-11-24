package com.happyhouse.service;

import com.happyhouse.model.Chat;
import com.happyhouse.model.Message;
import com.happyhouse.model.User;
import com.happyhouse.repository.ChatRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * Save a chat message to the database
     */
    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @PostConstruct
    public void createDefaultChatIfNotExists() {
        if (chatRepository.count() == 0) {
            logger.info("No chat found → creating the one and only house chat...");

            Chat defaultChat = new Chat();
            defaultChat.setChatName("Happy House");
            defaultChat.setMembers(new ArrayList<>());
            defaultChat.setMessages(new ArrayList<>());

            Chat saved = chatRepository.save(defaultChat);
            logger.info("Chat created successfully!");
            logger.info("YOUR CHAT ID → " + saved.getChatID());
            logger.info("Copy this ID into React file → const chatId = \"" + saved.getChatID() + "\";");
        } else {
            logger.info("Chat already exists. Nothing to do.");
        }
    }

    public Chat addMessageToChat(String chatId, Message message){
        Chat chat = chatRepository.findById(chatId).orElseThrow();
        chat.getMessages().add(message);
        return chatRepository.save(chat);
    }

    public List<Message> getMessages(String chatId) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        return chat.getMessages();
    }

    public Message sendMessage(String chatId, User sender, String content) {
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        Message message = new Message(sender, content);

        chat.addMessage(message);
        chatRepository.save(chat);

        return message;
    }


}