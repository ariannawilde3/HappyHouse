package com.happyhouse.controller;

import com.happyhouse.dto.UpdateProfileRequest;
import com.happyhouse.model.User;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

// handles REST API requests 
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    // declaring needed dependencies 
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    // maps the HTTP get requests to this method 
    @GetMapping("/me")
    // when the front end calls the url then this method will run
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        // skips the word Bearer
        String jwt = token.substring(7);
        //gets the users email
        String email = jwtUtil.extractEmail(jwt);
        //finds user in database by email
        User user = authService.findByEmail(email);
        // returns a user
        return ResponseEntity.ok(user);
    }

    // put is used for post updates aka creating
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(
            @RequestHeader("Authorization") String token,
            @Valid @RequestBody UpdateProfileRequest request) {
        //remove bearer
        String jwt = token.substring(7);
        // get email
        String email = jwtUtil.extractEmail(jwt);
        // find user
        User currentUser = authService.findByEmail(email);

        //update the user info
        User updatedUser = authService.updateProfile(currentUser.getId(), request);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping(value = "/anonymous-name", params = "email")
    public ResponseEntity<String> getAnonymousName(
            @RequestHeader("Authorization") String token,
            @RequestParam("email") String email) {
            User u = authService.findByEmail(email);
            String name = (u != null && u.getAnonymousUsername() != null)
                    ? u.getAnonymousUsername()
                    : "Anonymous";
            return ResponseEntity.ok(name); 
    }
}