package com.happyhouse.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

public class Chat {
    @Id
    String chatID;

    String chatName;
    List<User> members;
    List<Message> messages;

    public Chat() {
        this.members = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    //constructor
    public Chat(String chatID, String chatName, List<User> members, List<Message> messages) {
        this.chatID = chatID;
        this.chatName = chatName;
        this.members = members;
        this.messages = messages;
    }

    //getters
    public String getChatID() {
        return chatID;
    }

    public String getChatName() {
        return chatName;
    }

    public List<User> getMembers() {
        return members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    //setters
    public void setChatID(String chatID) {
        this.chatID = chatID;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}