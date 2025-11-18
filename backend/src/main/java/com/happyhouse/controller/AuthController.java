// labels file as part of the controllers folder
package com.happyhouse.controller;
// defines structure of requests to log in API
import com.happyhouse.dto.AuthResponse;
import com.happyhouse.dto.LoginRequest;
import com.happyhouse.dto.SignupRequest;

import com.happyhouse.service.AuthService;
import com.happyhouse.repository.UserRepository;

import java.io.IOException;
// imports valid email formats
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// helps to parse inputted info (emails, etc.)
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
// connects to Google's token info to verify users
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

@RestController // tells Spring that this file handles user interactions
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // deals with requests from frontend URL
// Controllers make sure our backend and frontend are connected, despite the name, they are more like boundaries than controllers
// Deals with all logic relating to user interactions with the specific page, seperate logic for use cases is in the services
public class AuthController {
    
    // Spring automatically creates an instance of the service
    private final AuthService authService;

    // and of the database
    private final UserRepository userRepository;

    private static final String ERR = "error";

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.authService = authService;
    }

    /**
     * Login endpoint
     * POST /api/auth/login
     */
    @PostMapping("/login")
    // deals with users trying to log in, transfers all control to authService (controller for logging in)
    // LoginRequest is email + password
    // @Valid checks email format
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Signup endpoint
     * POST /api/auth/signup
     */
    @PostMapping("/signup")
    // authorizes users trying to sign up
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        AuthResponse response = authService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Guest signup endpoint (no credentials required)
     * POST /api/auth/guest
     */
    @PostMapping("/guest")
    // authorizes users trying to register as guests
    public ResponseEntity<AuthResponse> guestSignup() {
        AuthResponse response = authService.guestSignup();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Google OAuth callback endpoint
     * POST /api/auth/google
     */
    @PostMapping("/google")
    // deals with logging in with Google
    public ResponseEntity<Object> googleLogin(@RequestBody Map<String, String> payload) {
        try {

            String credential = payload.get("credential");
            String googleId = payload.get("googleId");
            String email = payload.get("email");

            if (credential != null && !credential.isBlank()) {
                // Verifies ID token with Google API
                String verifyUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + credential;
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(java.net.URI.create(verifyUrl))
                        .GET()
                        .build();
                HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());

                String responseBody = resp.body();
                int statusCode = resp.statusCode();

                if (statusCode != 200) {
                    logger.info("Google tokeninfo returned status: {} body: {}", statusCode, responseBody);
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of(ERR, "Invalid ID token"));
                }

                // Parses responses to get google ids and emails
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> info = mapper.readValue(responseBody, Map.class);
                googleId = (String) info.get("sub");
                email = (String) info.get("email");

                // verifies aud matches client ids
                String aud = (String) info.get("aud");
                logger.info("Verified token aud={} sub={} email={}", aud, googleId, email);
            }

            if (googleId == null || email == null) {
                return ResponseEntity.badRequest().body(Map.of(ERR, "Missing googleId or email"));
            }

            // calls Google service
            AuthResponse response = authService.googleLogin(googleId, email);
            return ResponseEntity.ok(response);
        }  catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Google login interrupted: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(ERR, "Google login interrupted"));
        } catch (IOException e) {
            logger.error("Google login network error: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(Map.of(ERR, "Failed to verify Google token", "details", e.getMessage()));
        } catch (Exception e) {
                logger.error("Googel login failed: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of(ERR, "Google login failed", "details", e.getMessage()));
            }
        }
        
    /**
     * Refresh token endpoint
     * POST /api/auth/refresh
     */
    @PostMapping("/refresh")
    // deals with refresh button
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        
        if (refreshToken == null) {
            return ResponseEntity.badRequest().build();
        }
        
        AuthResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }
    
    /**
     * Test endpoint to verify authentication
     * GET /api/auth/me
     */
    @GetMapping("/me")
    public ResponseEntity<Map<String, String>> getCurrentUser() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "You are authenticated!");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/test")
    public ResponseEntity<Map<String, Object>> testConnection() {
        try {
            long userCount = userRepository.count();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Database connected successfully");
            response.put("userCount", userCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put(ERR, "Database connection failed: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
