package com.happyhouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
// does all of the stuff that shows up when backend is running properly and creates the health check for backend
public class HealthController {
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "OK");
        health.put("message", "HappyHouse Backend is running!");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "happyhouse-backend");
        health.put("version", "1.0.0");
        
        return ResponseEntity.ok(health);
    }
}
