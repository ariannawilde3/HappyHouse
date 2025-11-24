package junit;

import com.happyhouse.model.Comment;
import com.happyhouse.model.Post;
import com.happyhouse.model.User;
import com.happyhouse.repository.UserRepository;
import com.happyhouse.repository.PostRepository;
import com.happyhouse.service.CommentService;
import org.junit.Test;

import java.util.Optional;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestCommenting {

    @Test
    public void testCommenting() {
        UserRepository userRepository = mock(UserRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        Post testPost = new Post("Test Post", "Test content", new ArrayList<>());

        User testUser = new User();
        testUser.setId("123");
        testUser.setEmail("test@example.com");
        testUser.setAnonymousUsername("TestUser123");

        when(postRepository.findByObjID("post123")).thenReturn(Optional.of(testPost));
        when(userRepository.findById("123")).thenReturn(Optional.of(testUser));
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        CommentService testCommentService = new CommentService(postRepository, userRepository);
        Comment output = testCommentService.addComment("post123", "123", "this is a test comment");
        
        assertEquals("this is a test comment", output.getContent());
    }

    @Test
    public void testUpvotingComment() {
        UserRepository userRepository = mock(UserRepository.class);
        PostRepository postRepository = mock(PostRepository.class);

        Post testPost = new Post("Test Post 2", "Test content 2", new ArrayList<>());

        User testUser = new User();
        testUser.setId("456");
        testUser.setEmail("test2@example.com");
        testUser.setAnonymousUsername("TestUser456");

        when(postRepository.findByObjID("post456")).thenReturn(Optional.of(testPost));
        when(userRepository.findById("456")).thenReturn(Optional.of(testUser));
        when(postRepository.save(any(Post.class))).thenReturn(testPost);

        CommentService testCommentService = new CommentService(postRepository, userRepository);
        Comment output = testCommentService.addComment("post456", "456", "this will be upvoted");
        
        output.upvote("456");
        int votes = output.getVotes();
        
        assertEquals(1, votes);
    }
}