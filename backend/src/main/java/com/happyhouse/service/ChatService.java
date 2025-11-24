package com.happyhouse.service;

import com.happyhouse.model.Chat;
import com.happyhouse.model.Message;
import com.happyhouse.model.User;
import com.happyhouse.repository.ChatRepository;
import jakarta.annotation.PostConstruct;
import org.jboss.logging.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    /**
     * Save a chat message to the database
     */
    public Chat saveChat(Chat chat) {
        return chatRepository.save(chat);
    }

    @PostConstruct
    public void createDefaultChatIfNotExists() {
        if (chatRepository.count() == 0) {
            System.out.println("No chat found → creating the one and only house chat...");

            Chat defaultChat = new Chat();
            defaultChat.setChatName("Happy House");
            defaultChat.setMembers(new ArrayList<>());
            defaultChat.setMessages(new ArrayList<>());

            Chat saved = chatRepository.save(defaultChat);
            System.out.println("Chat created successfully!");
            System.out.println("YOUR CHAT ID → " + saved.getChatID());
            System.out.println("Copy this ID into React file → const chatId = \"" + saved.getChatID() + "\";");
        } else {
            System.out.println("Chat already exists. Nothing to do.");
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

//    public Chat addMessageToChat(Long chatId, Message message) {
//        Chat chat = chatRepository.findById(chatId).orElseThrow();
//        chat.getMessages().add(message);
//        return chatRepository.save(chat);
//    }


}