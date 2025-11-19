package com.happyhouse.dto;

import java.time.LocalDateTime;

public class CommentResponse {
    
    private String id;
    private String content;
    private String username; // Anonymous username
    private Integer votes;
    private LocalDateTime createdAt;
    private String userVote;
    
    public CommentResponse() {}
    
    public CommentResponse(String id, String content, String username, Integer votes, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.votes = votes;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public Integer getVotes() {
        return votes;
    }
    
    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public String getUserVote() {
        return userVote;
    }
    
    public void setUserVote(String userVote) {
        this.userVote = userVote;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}