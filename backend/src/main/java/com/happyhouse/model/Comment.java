package com.happyhouse.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Comment {
    
    private String id;
    private String content;
    private Integer votes = 0;
    private LocalDateTime createdAt;
    
    private String userId;
    private String anonymousUsername;

    private List<String> upvotedBy = new ArrayList<>();
    private List<String> downvotedBy = new ArrayList<>();

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

       public List<String> getUpvotedBy() {
        return upvotedBy;
    }
    
    public void setUpvotedBy(List<String> upvotedBy) {
        this.upvotedBy = upvotedBy;
    }
    
    public List<String> getDownvotedBy() {
        return downvotedBy;
    }
    
    public void setDownvotedBy(List<String> downvotedBy) {
        this.downvotedBy = downvotedBy;
    }
    
    public void upvote(String userId) {
        // Remove from downvoted if previously downvoted
        if (downvotedBy.contains(userId)) {
            downvotedBy.remove(userId);
            votes++;
        }
        
        // Add upvote if not already upvoted
        if (!upvotedBy.contains(userId)) {
            upvotedBy.add(userId);
            votes++;
        }
    }
    
    public void downvote(String userId) {
        // Remove from upvoted if previously upvoted
        if (upvotedBy.contains(userId)) {
            upvotedBy.remove(userId);
            votes--;
        }
        
        // Add downvote if not already downvoted
        if (!downvotedBy.contains(userId)) {
            downvotedBy.add(userId);
            votes--;
        }
    }

    public String getUserVote(String userId) {
        if (upvotedBy.contains(userId)) return "up";
        if (downvotedBy.contains(userId)) return "down";
        return null;
    }
}