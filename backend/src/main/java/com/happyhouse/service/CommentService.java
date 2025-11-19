package com.happyhouse.service;

import com.happyhouse.model.Comment;
import com.happyhouse.model.Post;
import com.happyhouse.model.User;
import com.happyhouse.repository.PostRepository;
import com.happyhouse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CommentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Add a comment to a post
     */
    public Comment addComment(String postId, String userId, String content) {
        // Find the post
        Post post = postRepository.findByObjID(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        // Find the user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Create new comment
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID().toString()); // Generate unique ID
        comment.setContent(content);
        comment.setUserId(userId);
        comment.setAnonymousUsername(user.getAnonymousUsername());
        comment.setVotes(0);
        comment.setCreatedAt(LocalDateTime.now());

        // Add comment to post
        post.addComment(comment);

        // Save post with new comment
        postRepository.save(post);

        return comment;
    }

    /**
     * Get all comments for a post
     */
    public List<Comment> getCommentsByPostId(String postId) {
        Post post = postRepository.findByObjID(postId)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + postId));

        return post.getComments();
    }

    /**
     * Upvote a comment
     */
    public Comment upvoteComment(String postId, String commentId, String userId) {
        Post post = postRepository.findByObjID(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = findCommentInPost(post, commentId);
        
        // Check if user already voted
        if (comment.getUpvotedBy().contains(userId)) {
            throw new RuntimeException("You have already upvoted this comment");
        }
        
        // Pass userId to upvote method
        comment.upvote(userId);
        postRepository.save(post);

        return comment;
    }

    /**
     * Downvote a comment
     */
    public Comment downvoteComment(String postId, String commentId, String userId) {
        Post post = postRepository.findByObjID(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        Comment comment = findCommentInPost(post, commentId);
        
        // Check if user already voted
        if (comment.getDownvotedBy().contains(userId)) {
            throw new RuntimeException("You have already downvoted this comment");
        }
        
        // Pass userId to downvote method
        comment.downvote(userId);
        postRepository.save(post);

        return comment;
    }

    /**
     * Helper method to find a comment in a post
     */
    private Comment findCommentInPost(Post post, String commentId) {
        return post.getComments().stream()
                .filter(c -> c.getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Comment not found with id: " + commentId));
    }
}