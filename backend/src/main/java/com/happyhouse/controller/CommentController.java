package com.happyhouse.controller;

import com.happyhouse.dto.CommentRequest;
import com.happyhouse.dto.CommentResponse;
import com.happyhouse.model.Comment;
import com.happyhouse.service.CommentService;
import com.happyhouse.model.User;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.AnonymousAuthenticationToken;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    private final CommentService commentService;

    public static final String ERR = "error";

    public static final String USER404 = "User not found";

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    /**
     * GET /api/posts/{postId}/comments
     * Get all comments for a post
     */
    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable String postId) {
        try {
            List<Comment> comments = commentService.getCommentsByPostId(postId);
            
            List<CommentResponse> response = comments.stream()
                    .map(this::convertToResponse)
                    .toList();
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * POST /api/posts/{postId}/comments
     * Add a new comment to a post
     */
    @PostMapping
    public ResponseEntity<Object> addComment(
            @PathVariable String postId,
            @Valid @RequestBody CommentRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        try {
            // Get authenticated user from Spring Security context
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, "Authentication required to add comments"));
            }
            
            // Get email from authentication (set by JWT filter)
            String userEmail = authentication.getName();
            
            if (userEmail == null || userEmail.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, "Invalid authentication token"));
            }
            
            // Get userId from email
            String userId = getUserIdFromEmail(userEmail);
            
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, USER404));
            }
            
            Comment comment = commentService.addComment(postId, userId, request.getContent());
            
            CommentResponse response = convertToResponse(comment);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(ERR, e.getMessage()));
        }
    }

    /**
     * POST /api/posts/{postId}/comments/{commentId}/upvote
     * Upvote a comment
     */
    @PostMapping("/{commentId}/upvote")
    public ResponseEntity<Object> upvoteComment(
            @PathVariable String postId,
            @PathVariable String commentId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, "Authentication required to vote"));
            }
            
            String userEmail = authentication.getName();
            String userId = getUserIdFromEmail(userEmail);
            
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, USER404));
            }
            
            Comment comment = commentService.upvoteComment(postId, commentId, userId);
            
            return ResponseEntity.ok(convertToResponse(comment));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(ERR, e.getMessage()));
        }
    }

    /**
     * POST /api/posts/{postId}/comments/{commentId}/downvote
     */
    @PostMapping("/{commentId}/downvote")
    public ResponseEntity<Object> downvoteComment(
            @PathVariable String postId,
            @PathVariable String commentId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication instanceof AnonymousAuthenticationToken) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, "Authentication required to vote"));
            }
            
            String userEmail = authentication.getName();
            String userId = getUserIdFromEmail(userEmail);
            
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, USER404));
            }
            
            Comment comment = commentService.downvoteComment(postId, commentId, userId);
            
            return ResponseEntity.ok(convertToResponse(comment));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(ERR, e.getMessage()));
        }
    }

    /**
     * Helper method to get userId from email using UserRepository
     */
    @Autowired
    private com.happyhouse.repository.UserRepository userRepository;

    private String getUserIdFromEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .map(User::getId)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Helper method to convert Comment to CommentResponse
     */
    private CommentResponse convertToResponse(Comment comment) {
        // Get current user to check their vote
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication != null ? authentication.getName() : null;
        String userId = userEmail != null ? getUserIdFromEmail(userEmail) : null;
        
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setUsername(comment.getAnonymousUsername());
        response.setVotes(comment.getVotes());
        response.setCreatedAt(comment.getCreatedAt());
        
        // Set user's vote status
        if (userId != null) {
            response.setUserVote(comment.getUserVote(userId));
        }
        
        return response;
    }
}