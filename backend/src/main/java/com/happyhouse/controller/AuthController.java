package com.happyhouse.controller;

import com.happyhouse.dto.AuthResponse;
import com.happyhouse.dto.LoginRequest;
import com.happyhouse.dto.SignupRequest;
import com.happyhouse.service.AuthService;
import com.happyhouse.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

@RestController // tells Spring that this file handles user interactions
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // deals with requests from frontend URL
// Controllers make sure our backend and frontend are connected, despite the name, they are more like boundaries than controllers
// Deals with all logic relating to user interactions with the specific page, seperate logic for use cases is in the services
public class AuthController {
    
    @Autowired // Spring automatically creates an instance of the service
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;
    
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
    public ResponseEntity<AuthResponse> signup(@Valid @RequestBody SignupRequest signupRequest) {
        AuthResponse response = authService.signup(signupRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Guest signup endpoint (no credentials required)
     * POST /api/auth/guest
     */
    @PostMapping("/guest")
    public ResponseEntity<AuthResponse> guestSignup() {
        AuthResponse response = authService.guestSignup();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Google OAuth callback endpoint
     * POST /api/auth/google
     */
    @PostMapping("/google")
    public ResponseEntity<?> googleLogin(@RequestBody Map<String, String> payload) {
        try {
            // Debug log for incoming payload
            System.out.println("POST /api/auth/google payload: " + payload);

            String credential = payload.get("credential"); // preferred: ID token from GSI
            String googleId = payload.get("googleId");
            String email = payload.get("email");

            if (credential != null && !credential.isBlank()) {
                // Verify ID token with Google's tokeninfo endpoint
                String verifyUrl = "https://oauth2.googleapis.com/tokeninfo?id_token=" + credential;
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(java.net.URI.create(verifyUrl))
                        .GET()
                        .build();
                HttpResponse<String> resp = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (resp.statusCode() != 200) {
                    System.out.println("Google tokeninfo returned status: " + resp.statusCode() + " body: " + resp.body());
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(Map.of("error", "Invalid ID token"));
                }

                // Parse response JSON to get sub (google id) and email
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> info = mapper.readValue(resp.body(), Map.class);
                googleId = (String) info.get("sub");
                email = (String) info.get("email");

                // Optional: verify aud matches your client id
                String aud = (String) info.get("aud");
                System.out.println("Verified token aud=" + aud + " sub=" + googleId + " email=" + email);
            }

            if (googleId == null || email == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Missing googleId or email"));
            }

            // call your service
            AuthResponse response = authService.googleLogin(googleId, email);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Google login failed", "details", e.getMessage()));
        }
    }
        
    /**
     * Refresh token endpoint
     * POST /api/auth/refresh
     */
    @PostMapping("/refresh")
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
    public ResponseEntity<?> testConnection() {
        try {
            long userCount = userRepository.count();
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Database connected successfully");
            response.put("userCount", userCount);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Database connection failed: " + e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }
}
