package junit;

import com.happyhouse.dto.AuthResponse;
import com.happyhouse.dto.LoginRequest;
import com.happyhouse.model.User;
import com.happyhouse.repository.UserRepository;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;
import org.junit.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestLoggingIn {

    @Test
    public void testLoggingInWithDatabase() {
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        User testUser = new User();
        testUser.setId("123");
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("$2a$10$encodedPasswordHash");
        testUser.setAuthProvider(User.AuthProvider.LOCAL);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@gmail.com");
        loginRequest.setPassword("test1234");
    
        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("test1234", "$2a$10$encodedPasswordHash")).thenReturn(true);
        when(jwtUtil.generateToken("test@gmail.com", "123")).thenReturn("mock-jwt-token");

        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtUtil);
        AuthResponse output = authService.login(loginRequest);

        assertEquals("test@gmail.com", output.getEmail());
    }

    @Test
    public void testLoggingInWithGoogle() {
        UserRepository userRepository = mock(UserRepository.class);
        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        
        User googleUser = new User();
        googleUser.setId("456");
        googleUser.setEmail("google@example.com");
        googleUser.setGoogleId("google-oauth-id-123");
        googleUser.setAuthProvider(User.AuthProvider.GOOGLE);
    
        when(userRepository.findByGoogleId("google-oauth-id-123")).thenReturn(Optional.of(googleUser));
        when(jwtUtil.generateToken("google@example.com", "456")).thenReturn("google-jwt-token");

        AuthService authService = new AuthService(userRepository, passwordEncoder, jwtUtil);
        AuthResponse output = authService.googleLogin("google-oauth-id-123", "google@example.com");

        assertEquals("google@example.com", output.getEmail());
    }
}