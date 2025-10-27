package com.happyhouse.controller;

import com.happyhouse.dto.AuthResponse;
import com.happyhouse.dto.LoginRequest;
import com.happyhouse.dto.SignupRequest;
import com.happyhouse.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService authService;
    
    /**
     * Login endpoint
     * POST /api/auth/login
     */
    @PostMapping("/login")
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
    public ResponseEntity<AuthResponse> googleLogin(@RequestBody Map<String, String> payload) {
        String googleId = payload.get("googleId");
        String email = payload.get("email");
        
        if (googleId == null || email == null) {
            return ResponseEntity.badRequest().build();
        }
        
        AuthResponse response = authService.googleLogin(googleId, email);
        return ResponseEntity.ok(response);
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
}
