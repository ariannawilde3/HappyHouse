package com.happyhouse.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.happyhouse.repository.AllGroupChats;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;
import com.happyhouse.model.GCInfo;
import com.happyhouse.model.User;


@RestController
@RequestMapping("/api/gcc")
//@CrossOrigin(origins = "http://localhost:5173")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
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
        } while (repo != null && repo.existsByInviteCode(code));
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
        return repo.findByInviteCode(code)
        .map(gc -> !gc.getUnlocked()
            && gc.getCurrentRoomieCount() < gc.getExpectedRoomieCount())
        .orElse(false);
    }

    @GetMapping("/by-code/{code}")
    public ResponseEntity<GCInfo> getByInviteCode(@PathVariable int code) {
        return repo.findByInviteCode(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/me")
    public ResponseEntity<GCInfo> getMyGroupChat(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();  // no string body
        }

        final String jwt = authHeader.substring(7);
        final String email = jwtUtil.extractEmail(jwt); // wrap in try/catch if you want 401 instead of 500

        final User user = authService.findByEmail(email);
        if (user == null || user.getGroupChatCode() <= 0) {
            return ResponseEntity.status(404).build();
        }

        return repo.findByInviteCode(user.getGroupChatCode())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/join/{code}")
public ResponseEntity<GCInfo> join(@PathVariable int code,
                                   @RequestHeader("Authorization") String authHeader) {
    // who is joining?
    String email = jwtUtil.extractEmail(authHeader.substring(7));
    User user = authService.findByEmail(email);

    // load GC
    return repo.findByInviteCode(code)
        .map(gc -> {
            // avoid double-count if user is already in this GC
            if (user != null && user.getGroupChatCode() == code) {
                return ResponseEntity.ok(gc);
            }

            // increment (naive; good enough for small apps)
            if (gc.getCurrentRoomieCount() < gc.getExpectedRoomieCount()) {
                gc.addToCurrentRoomieCount();
                if (gc.getCurrentRoomieCount() >= gc.getExpectedRoomieCount()) {
                    gc.setUnlocked(true);
                }
                repo.save(gc);
            }

            // attach user to this GC
            if (user != null) {
                authService.updateGroupChatCodeByEmail(email, code);
            }

            return ResponseEntity.ok(gc);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
}
}
