package com.happyhouse.service;

import com.happyhouse.model.User;
import com.happyhouse.repository.UserRepository;
import com.happyhouse.util.AnonymousUsernameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnonymousUsernameScheduler {
    
    private static final Logger logger = LoggerFactory.getLogger(AnonymousUsernameScheduler.class);
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Scheduled task to regenerate all anonymous usernames at midnight (00:00:00) every day
     * Cron expression: 0 0 0 * * ? (second minute hour day month weekday)
     */
    @Scheduled(cron = "${anonymous.username.regeneration.cron}")
    public void regenerateAllAnonymousUsernames() {
        logger.info("Starting scheduled anonymous username regeneration at {}", LocalDateTime.now());
        
        try {
            // Get all users
            List<User> users = userRepository.findAll();
            int updatedCount = 0;
            
            // Regenerate anonymous username for each user
            for (User user : users) {
                String newUsername = AnonymousUsernameGenerator.generate();
                user.setAnonymousUsername(newUsername);
                user.setAnonymousUsernameGeneratedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                userRepository.save(user);
                updatedCount++;
            }
            
            logger.info("Successfully regenerated anonymous usernames for {} users", updatedCount);
            
        } catch (Exception e) {
            logger.error("Error during anonymous username regeneration: {}", e.getMessage(), e);
        }
    }
    
    /**
     * Manual trigger for regenerating a specific user's anonymous username
     * This can be called when a user logs in and their username is outdated
     */
    public void regenerateUsernameForUser(User user) {
        String newUsername = AnonymousUsernameGenerator.generate();
        user.setAnonymousUsername(newUsername);
        user.setAnonymousUsernameGeneratedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
        
        logger.debug("Regenerated anonymous username for user {}: {}", user.getId(), newUsername);
    }
}
