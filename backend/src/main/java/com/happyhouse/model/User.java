package com.happyhouse.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
// Models are the Entities of our system
public class User {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String email;
    
    private String name; 

    private String password;
    
    private String anonymousUsername;
    
    private LocalDateTime anonymousUsernameGeneratedAt;
    
    private UserType userType;
    
    private AuthProvider authProvider;
    
    private String googleId;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private int groupChatID = 0;
    
    private List<String> postIds = new ArrayList<>();
    
    private boolean isActive = true;

    
    // Constructors
    public User() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.userType = UserType.REGULAR;
        this.authProvider = AuthProvider.LOCAL;
    }
    
    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getAnonymousUsername() {
        return anonymousUsername;
    }
    
    public void setAnonymousUsername(String anonymousUsername) {
        this.anonymousUsername = anonymousUsername;
        this.anonymousUsernameGeneratedAt = LocalDateTime.now();
    }
    
    public LocalDateTime getAnonymousUsernameGeneratedAt() {
        return anonymousUsernameGeneratedAt;
    }
    
    public void setAnonymousUsernameGeneratedAt(LocalDateTime anonymousUsernameGeneratedAt) {
        this.anonymousUsernameGeneratedAt = anonymousUsernameGeneratedAt;
    }
    
    public UserType getUserType() {
        return userType;
    }
    
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    public AuthProvider getAuthProvider() {
        return authProvider;
    }
    
    public void setAuthProvider(AuthProvider authProvider) {
        this.authProvider = authProvider;
    }
    
    public String getGoogleId() {
        return googleId;
    }
    
    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    
    public List<String> getPostIds() {
        return postIds;
    }
    
    public void setPostIds(List<String> postIds) {
        this.postIds = postIds;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }

    public void setGroupChatCode(int a) {
        groupChatID = a;
    }

    public int getGroupChatCode() {
        return groupChatID;
    }
    
    // Enums
    public enum UserType {
        GUEST,
        REGULAR,
        ADMIN
    }
    
    public enum AuthProvider {
        LOCAL,
        GOOGLE
    }
}
