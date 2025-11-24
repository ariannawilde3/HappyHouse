package com.happyhouse.controller;

//TODO: post list just returns previews

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.happyhouse.model.Post;
import com.happyhouse.model.User;
import com.happyhouse.repository.PostRepository;
import com.happyhouse.repository.UserRepository;
import org.springframework.data.domain.*;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/api/viewpost")
@CrossOrigin(origins = "*")
public class PostGetController {

	private final com.happyhouse.repository.UserRepository userRepository;

    public static final String ERR = "error";

    public static final String USER404 = "User not found";

    public static final String POST404 = "Post not found";
	
	private final PostRepository postrepo;
	@Autowired
	public PostGetController(PostRepository postrepo, UserRepository userRepository) {
		this.postrepo = postrepo;
		this.userRepository = userRepository;
	}

	@GetMapping("/all/{page}")
	public List<Post> getPostList(@PathVariable int page) {
		Pageable pageable = PageRequest.of(page, 3, Sort.by("_id").descending());
		return postrepo.findAll(pageable).getContent();
	}

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) {
        return postrepo.findById(id)
		.orElseThrow(() -> new RuntimeException(POST404));
    }
	
	@GetMapping("/add")
	public Post addPost() {
		Post newPost = new Post(
		"test content",
		"test title",
		new ArrayList<>(Arrays.asList("test tag", "two tag")));
		
		postrepo.save(newPost);
		return newPost;
	}

	/**
     * POST /api/posts/{postId}/upvote
     * Upvote a post
     */
    @PostMapping("/{id}/upvote")
    public ResponseEntity<Object> upvoteComment(@PathVariable String id) {
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
            
            // Find post
            Post post = postrepo.findByObjID(id)
                    .orElseThrow(() -> new RuntimeException(POST404));

            // Check if already downvoted
            if (post.getUpvotedBy().contains(userId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(ERR, "You have already upvoted this post"));
            }

            // Apply downvote
            post.upvote(userId);
            postrepo.save(post);
			
			return ResponseEntity.ok(post);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(ERR, e.getMessage()));
        }
    }

    /**
     * POST /api/posts/{postId}/downvote
     */
    @PostMapping("/{id}/downvote")
    public ResponseEntity<Object> downvoteComment(@PathVariable String id) {
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
            
            // Find post
            Post post = postrepo.findByObjID(id)
                    .orElseThrow(() -> new RuntimeException(POST404));

            // Check if already downvoted
            if (post.getDownvotedBy().contains(userId)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of(ERR, "You have already downvoted this post"));
            }

            // Apply downvote
            post.downvote(userId);
            postrepo.save(post);

            return ResponseEntity.ok(post);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(ERR, e.getMessage()));
        }
    }

    /**
     * Helper method to get userId from email using UserRepository
     */
    private String getUserIdFromEmail(String email) {
        try {
            return userRepository.findByEmail(email)
                    .map(User::getId)
                    .orElse(null);
        } catch (Exception e) {
            return null;
        }
    }
}