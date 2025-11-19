package com.happyhouse.model;

import java.time.LocalDateTime;

public class Comment {
    
    private String id;
    private String content;
    private Integer votes = 0;
    private LocalDateTime createdAt;
    
    private String userId;
    private String anonymousUsername;

    public Comment() {
        this.votes = 0;
        this.createdAt = LocalDateTime.now();
    }
    
    public Comment(String content, String userId, String anonymousUsername) {
        this.content = content;
        this.userId = userId;
        this.anonymousUsername = anonymousUsername;
        this.votes = 0;
        this.createdAt = LocalDateTime.now();
    }

    // getters and setters
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

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

        public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnonymousUsername() {
        return anonymousUsername;
    }

    public void setAnonymousUsername(String anonymousUsername) {
        this.anonymousUsername = anonymousUsername;
    }
    
    public void upvote() {
        if (this.votes == null) this.votes = 0;
        this.votes++;
    }
    
    public void downvote() {
        if (this.votes == null) this.votes = 0;
        this.votes--;
    }
}