package com.happyhouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.happyhouse.repository.AllGroupChats;
import com.happyhouse.repository.UserRepository;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;
import com.happyhouse.model.GCInfo;
import com.happyhouse.model.User;


@RestController
@RequestMapping("/api/gcc")
public class CreateJoinGCController {

    private final AllGroupChats repo;

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public CreateJoinGCController(AllGroupChats repo, AuthService authService, JwtUtil jwtUtil) {
        this.repo = repo;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }


    //creates a new groupchat and invitecode
    @PostMapping("/")
    public int getSettings(@RequestBody GCInfo form, 
            @RequestHeader("Authorization") String authHeader) {
        int code; 
        do { //checks to see if code exists
            code = form.createInviteCode();
        } while (repo.existsByInviteCode(code));
        form.addToCurrentRoomieCount(); //initiates as 1
        repo.save(form); //adds to repo

        String jwt = authHeader.substring(7); // remove "Bearer "
        String email = jwtUtil.extractEmail(jwt);
        authService.updateGroupChatCodeByEmail(email, code); 

        return form.getInviteCode();  //return for the pgc settings page
    }


    //looks for a specific code
    @GetMapping("/exists/{code}")
    public boolean checkInviteCode(@PathVariable int code) {
        return repo.existsByInviteCode(code);
    }


}