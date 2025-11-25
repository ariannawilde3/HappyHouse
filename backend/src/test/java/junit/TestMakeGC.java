package junit;

import com.happyhouse.controller.CreateJoinGCController;
import com.happyhouse.dto.CreateGCRequest;
import com.happyhouse.model.GCInfo;
import com.happyhouse.repository.AllGroupChats;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestMakeGC {

    @Test
    public void testCreatingGC() {
        // fake dependencies
        AllGroupChats repo = mock(AllGroupChats.class);
        AuthService authService = mock(AuthService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);
        CreateJoinGCController controller = new CreateJoinGCController(repo, authService, jwtUtil);

        // call and set everything
        CreateGCRequest req = new CreateGCRequest();
        req.setHouseName("My House");
        req.setExpectedRoomieCount(3);

        when(jwtUtil.extractEmail("token123")).thenReturn("test@example.com");
        when(repo.save(any(GCInfo.class))).thenAnswer(i -> i.getArguments()[0]);

        // get code to check everything worked
        int code = controller.getSettings(req, "Bearer token123");
        assertTrue(code >= 100000 && code <= 999999);
    }

    @Test
    public void testCreatingGCWithInvalidData() {
        // fake dependencies
        AllGroupChats repo = mock(AllGroupChats.class);
        AuthService authService = mock(AuthService.class);
        JwtUtil jwtUtil = mock(JwtUtil.class);

        CreateJoinGCController controller = new CreateJoinGCController(repo, authService, jwtUtil);

        // invalid data
        CreateGCRequest req = new CreateGCRequest();
        req.setHouseName(null);
        req.setExpectedRoomieCount(0);

        when(repo.save(any(GCInfo.class))).thenAnswer(i -> i.getArguments()[0]);
        when(jwtUtil.extractEmail("badtoken")).thenThrow(new RuntimeException("Invalid JWT"));

        try {
            controller.getSettings(req, "Bearer badtoken");
            fail("Expected RuntimeException for invalid token");
        } catch (RuntimeException e) {
            assertEquals("Invalid JWT", e.getMessage());
        }
    }
}
