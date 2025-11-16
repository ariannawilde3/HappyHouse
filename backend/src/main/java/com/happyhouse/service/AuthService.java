package com.happyhouse.service;


import com.happyhouse.dto.UpdateProfileRequest;
import com.happyhouse.dto.AuthResponse;
import com.happyhouse.dto.LoginRequest;
import com.happyhouse.dto.SignupRequest;
import com.happyhouse.exception.BadRequestException;
import com.happyhouse.exception.ResourceNotFoundException;
import com.happyhouse.model.User;
import com.happyhouse.repository.UserRepository;
import com.happyhouse.util.AnonymousUsernameGenerator;
import com.happyhouse.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired; 

import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    private static final String USER_NOT_FOUND = "User not found";

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
    }
    
    /**
     * Login with email and password
     */
    public AuthResponse login(LoginRequest loginRequest) {
        
        // Get user from database
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        
        // Check if anonymous username needs regeneration (daily at midnight)
        checkAndRegenerateAnonymousUsername(user);
        
        // Generate tokens
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());
        
        // Return response
        return new AuthResponse(
            token,
            refreshToken,
            user.getId(),
            user.getEmail(),
            user.getAnonymousUsername(),
            user.getUserType()
        );
    }
    
    /**
     * Register new user
     */
    public AuthResponse signup(SignupRequest signupRequest) {
        // Check if email already exists or is null
        if (signupRequest == null) {
            throw new NullPointerException("No email provided");
        }
        if (userRepository == null) {
            throw new NullPointerException("User does not exist");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setUserType(User.UserType.REGULAR);
        user.setAuthProvider(User.AuthProvider.LOCAL);
        user.setAnonymousUsername(AnonymousUsernameGenerator.generate());
        user.setAnonymousUsernameGeneratedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setActive(true);

        // Save user
        userRepository.save(user);
        
        // Generate tokens
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());
        
        // Return response
        return new AuthResponse(
            token,
            refreshToken,
            user.getId(),
            user.getEmail(),
            user.getAnonymousUsername(),
            user.getUserType()
        );
    }
    
    /**
     * Register as guest (no email/password required)
     */
    public AuthResponse guestSignup() {
        // Create guest user with unique identifier
        User user = new User();
        user.setEmail("guest_" + System.currentTimeMillis() + "@happyhouse.local");
        user.setUserType(User.UserType.GUEST);
        user.setAuthProvider(User.AuthProvider.LOCAL);
        user.setAnonymousUsername(AnonymousUsernameGenerator.generate());
        user.setAnonymousUsernameGeneratedAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        // Save user
        user = userRepository.save(user);
        
        // Generate tokens
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());
        
        // Return response
        return new AuthResponse(
            token,
            refreshToken,
            user.getId(),
            user.getEmail(),
            user.getAnonymousUsername(),
            user.getUserType()
        );
    }
    
    /**
     * Handle Google OAuth login
     */
    public AuthResponse googleLogin(String googleId, String email) {
        // Check if user exists
        User user = userRepository.findByGoogleId(googleId)
                .orElseGet(() -> {
                    // Create new user
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setGoogleId(googleId);
                    newUser.setUserType(User.UserType.REGULAR);
                    newUser.setAuthProvider(User.AuthProvider.GOOGLE);
                    newUser.setAnonymousUsername(AnonymousUsernameGenerator.generate());
                    newUser.setAnonymousUsernameGeneratedAt(LocalDateTime.now());
                    newUser.setCreatedAt(LocalDateTime.now());
                    newUser.setUpdatedAt(LocalDateTime.now());
                    return userRepository.save(newUser);
                });
        
        // Check if anonymous username needs regeneration
        checkAndRegenerateAnonymousUsername(user);
        
        // Generate tokens
        String token = jwtUtil.generateToken(user.getEmail(), user.getId());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail(), user.getId());
        
        // Return response
        return new AuthResponse(
            token,
            refreshToken,
            user.getId(),
            user.getEmail(),
            user.getAnonymousUsername(),
            user.getUserType()
        );
    }
    
    /**
     * Check and regenerate anonymous username if needed (every 24 hours at midnight)
     */
    private void checkAndRegenerateAnonymousUsername(User user) {
        LocalDateTime lastGenerated = user.getAnonymousUsernameGeneratedAt();
        LocalDateTime now = LocalDateTime.now();
        
        // Check if it's a new day (after midnight)
        if (lastGenerated == null || 
            lastGenerated.toLocalDate().isBefore(now.toLocalDate())) {
            
            // Generate new anonymous username
            user.setAnonymousUsername(AnonymousUsernameGenerator.generate());
            user.setAnonymousUsernameGeneratedAt(now);
            user.setUpdatedAt(now);
            userRepository.save(user);
        }
    }


    //updates user group chat code
    public User updateGroupChatCodeByEmail(String email, int code) {
        User user = findByEmail(email);        
        user.setGroupChatCode(code); // given
        return userRepository.save(user);      //utilizing connection to repo
    }
    
    /**
     * Refresh access token using refresh token
     */
    public AuthResponse refreshToken(String refreshToken) {
        if (refreshToken == null || !jwtUtil.validateToken(refreshToken)) {
            throw new BadRequestException("Invalid refresh token");
        }
        
        String email = jwtUtil.extractEmail(refreshToken);
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND));
        
        // Generate new access token
        String newToken = jwtUtil.generateToken(user.getEmail(), user.getId());
        
        return new AuthResponse(
            newToken,
            refreshToken,
            user.getId(),
            user.getEmail(),
            user.getAnonymousUsername(),
            user.getUserType()
        );
    }

    public User updateProfile(String userId, UpdateProfileRequest request) {

        // find the user in the database 
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException(USER_NOT_FOUND));
    
        // update the username in the database 
        user.setName(request.getName());
    
        // if the email is changed then update the email
        if (!user.getEmail().equals(request.getEmail())) {
            // second check to see if the email is already being used
            if (userRepository.findByEmail(request.getEmail()).isPresent()) {
                throw new IllegalArgumentException("Email already in use");
            }
            // set the email
            user.setEmail(request.getEmail());
        }
    
        // if the password is ot be changed, change the password 
        if (request.getCurrentPassword() != null && !request.getCurrentPassword().isEmpty()) {

            //if current password not inputted correctly through error
            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Current password is incorrect");
            }
            
            // check password length requirments 
            if (request.getNewPassword() == null || request.getNewPassword().length() < 6) {
                throw new IllegalArgumentException("New password must be at least 6 characters");
            }
            
            // set password 
            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }
        
        // save user data in database 
        return userRepository.save(user);
    }



}
