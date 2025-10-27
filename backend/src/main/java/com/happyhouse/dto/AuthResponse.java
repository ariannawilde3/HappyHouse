package com.happyhouse.dto;

import com.happyhouse.model.User;

public class AuthResponse {
    
    private String token;
    private String refreshToken;
    private String type = "Bearer";
    private String userId;
    private String email;
    private String anonymousUsername;
    private User.UserType userType;
    
    // Constructors
    public AuthResponse() {
    }
    
    public AuthResponse(String token, String refreshToken, String userId, String email, 
                       String anonymousUsername, User.UserType userType) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.userId = userId;
        this.email = email;
        this.anonymousUsername = anonymousUsername;
        this.userType = userType;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getUserId() {
        return userId;
    }
    
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAnonymousUsername() {
        return anonymousUsername;
    }
    
    public void setAnonymousUsername(String anonymousUsername) {
        this.anonymousUsername = anonymousUsername;
    }
    
    public User.UserType getUserType() {
        return userType;
    }
    
    public void setUserType(User.UserType userType) {
        this.userType = userType;
    }
}
