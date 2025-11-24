package junit;

import com.happyhouse.dto.UpdateProfileRequest;
import com.happyhouse.model.User;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;
import com.happyhouse.controller.UserController;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UpdateProfileTest {

    @Test
    public void testUpdateProfile() {
        AuthService authService = mock(AuthService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        User testUser = new User();
        testUser.setId("user123");
        testUser.setEmail("test@example.com");
        testUser.setAnonymousUsername("testuser");

        User updatedUser = new User();
        updatedUser.setId("user123");
        updatedUser.setEmail("test@example.com");
        updatedUser.setAnonymousUsername("NewuseRname");

        UpdateProfileRequest req = new UpdateProfileRequest();

        when(jwtUtil.extractEmail("testtoken123")).thenReturn("test@example.com");
        when(authService.findByEmail("test@example.com")).thenReturn(testUser);
        when(authService.updateProfile("user123", req)).thenReturn(updatedUser);

        UserController userController = new UserController(authService, jwtUtil);
        User output = userController.updateProfile("Bearer testtoken123", req).getBody();

        assertEquals("NewuseRname", output.getAnonymousUsername());
    }

    @Test
    public void testGetCurrentUser() {
        AuthService authService = mock(AuthService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        User testUser = new User();
        testUser.setId("user123");
        testUser.setEmail("test@example.com");
        testUser.setAnonymousUsername("testuser");

        when(jwtUtil.extractEmail("testtoken123")).thenReturn("test@example.com");
        when(authService.findByEmail("test@example.com")).thenReturn(testUser);

        UserController userController = new UserController(authService, jwtUtil);
        User output = userController.getCurrentUser("Bearer testtoken123").getBody();

        assertEquals("test@example.com", output.getEmail());
        assertEquals("testuser", output.getAnonymousUsername());
    }
}








