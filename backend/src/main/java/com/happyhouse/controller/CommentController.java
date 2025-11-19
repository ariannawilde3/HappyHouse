package com.happyhouse.controller;

import com.happyhouse.dto.CommentRequest;
import com.happyhouse.dto.CommentResponse;
import com.happyhouse.model.Comment;
import com.happyhouse.service.CommentService;
import com.happyhouse.util.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private JwtUtil jwtUtil;

    public static final String ERR = "error";

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
                    .collect(Collectors.toList());
            
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
            Integer userId = extractUserIdFromToken(authHeader);
            
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, "Authentication required to add comments"));
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
            Integer userId = extractUserIdFromToken(authHeader);
            
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, "Authentication required to vote"));
            }
            
            Comment comment = commentService.upvoteComment(postId, commentId);
            
            return ResponseEntity.ok(convertToResponse(comment));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(ERR, e.getMessage()));
        }
    }

    /**
     * POST /api/posts/{postId}/comments/{commentId}/downvote
     * Downvote a comment
     */
    @PostMapping("/{commentId}/downvote")
    public ResponseEntity<Object> downvoteComment(
            @PathVariable String postId,
            @PathVariable String commentId,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        try {
            Integer userId = extractUserIdFromToken(authHeader);
            
            if (userId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of(ERR, "Authentication required to vote"));
            }
            
            Comment comment = commentService.downvoteComment(postId, commentId);
            
            return ResponseEntity.ok(convertToResponse(comment));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(ERR, e.getMessage()));
        }
    }

    /**
     * Helper method to convert Comment to CommentResponse
     */
    private CommentResponse convertToResponse(Comment comment) {
        CommentResponse response = new CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setUsername(comment.getAnonymousUsername());
        response.setVotes(comment.getVotes());
        response.setCreatedAt(comment.getCreatedAt());
        return response;
    }

    /**
     * Helper method to extract userId from JWT token
     */
    private Integer extractUserIdFromToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null;
        }
    
        try {
            String token = authHeader.substring(7);
            String userIdString = jwtUtil.extractUserId(token);
            return Integer.parseInt(userIdString);
        } catch (Exception e) {
            return null;
        }
    }
}