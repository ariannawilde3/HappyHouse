package com.happyhouse.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Message {
    String messageID;
    User sender;
    String content;
    LocalDateTime timeStamp;

    //constructor
    public Message(User sender, String content) {
        this.messageID = UUID.randomUUID().toString();
        this.sender = sender;
        this.content = content;
        this.timeStamp = LocalDateTime.now();
    }

    public Message(String messageID, User sender, String content) {
        this.messageID = messageID;
        this.sender = sender;
        this.content = content;
        this.timeStamp = LocalDateTime.now();
    }

    //getters
    public String getMessageID() {
        return messageID;
    }

    public User getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    //setters
    public void setMessageID(String messageID) {
        this.messageID = messageID;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}