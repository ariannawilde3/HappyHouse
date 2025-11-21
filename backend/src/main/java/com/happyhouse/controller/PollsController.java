package com.happyhouse.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyhouse.repository.PollsRepository;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;
import com.happyhouse.dto.PollRequest;
import com.happyhouse.model.Poll;
import com.happyhouse.model.User;


@RestController
@RequestMapping("/api/pollMV")
@CrossOrigin(origins = "http://localhost:5173")
public class PollsController {

    private final PollsRepository repo; 
    private final AuthService authService;
    private final JwtUtil jwtUtil;
        public static class VoteRequest {
        public int option;
    }


    
    public PollsController(PollsRepository repo, AuthService authService, JwtUtil jwtUtil) {
        this.repo = repo;
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/")
    public void createPoll(@RequestBody PollRequest req, @RequestHeader(name = "Authorization") String authHeader) {
        String jwt = authHeader.substring(7); // remove "Bearer "
        String email = jwtUtil.extractEmail(jwt);
        User creator = authService.findByEmail(email);



        
        Poll poll = new Poll(req.getTitle(), req.getVoteOption1(), req.getVoteOption2(), email, creator.getGroupChatCode());
        repo.save(poll);
    }


    @PostMapping("/{id}/vote")
    public ResponseEntity<?> vote(
            @RequestHeader("Authorization") String token,
            @PathVariable("id") String pollId,
            @RequestBody VoteRequest req) {
        String jwt = token.substring(7);
        String voterEmail = jwtUtil.extractEmail(jwt);

        Poll poll = repo.findById(pollId).orElse(null);
        if (poll == null) return ResponseEntity.notFound().build();

        if (poll.getVoters().contains(voterEmail)) {
            return ResponseEntity.badRequest().body("You already voted on this poll.");
        }

        // first vote on THIS poll by THIS user
        if (req.option == 1) poll.addToVotesFor1(); else poll.addToVotesFor2();
        poll.addToTotalVotes(); 
        poll.getVoters().add(voterEmail);

        repo.save(poll);
        
        // Return PollView instead of Poll
        PollView view = new PollView(
            poll.getPollID(),
            poll.getTitle(),
            poll.getVoteOption1(),
            poll.getVoteOption2(),
            poll.getTimeUntilTimeout(),
            poll.getTotalVotes(),
            poll.getVotesFor1(),
            poll.getVotesFor2(),
            poll.getEmailOfCreator(),
            true
        );
        return ResponseEntity.ok(view);
    }

    record PollView(
        String id,
        String title,
        String voteOpt1,
        String voteOpt2,
        int timeUntilTimeout,
        int totalVotes,
        int votesFor1,
        int votesFor2,
        String emailOfCreator,
        boolean hasVoted
) {}

    @GetMapping
    public ResponseEntity<List<PollView>> getAllPolls(@RequestHeader("Authorization") String authHeader) {
        String jwt = authHeader.substring(7);
        String email = jwtUtil.extractEmail(jwt);
        User user = authService.findByEmail(email);

        var polls = repo.findByGroupChatId(user.getGroupChatCode());
        var out = polls.stream().map(p -> new PollView(
                p.getPollID(),
                p.getTitle(),
                p.getVoteOption1(),
                p.getVoteOption2(),
                p.getTimeUntilTimeout(),
                p.getTotalVotes(),
                p.getVotesFor1(),
                p.getVotesFor2(),
                p.getEmailOfCreator(),
                p.getVoters() != null && p.getVoters().contains(email) // <-- per-poll flag
        )).toList();

        return ResponseEntity.ok(out);
    }

    }