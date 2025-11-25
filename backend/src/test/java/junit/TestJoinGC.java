package junit;

import com.happyhouse.controller.CreateJoinGCController;
import com.happyhouse.model.GCInfo;
import com.happyhouse.model.User;
import com.happyhouse.repository.AllGroupChats;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TestJoinGC {

    @Test
    public void testJoiningGC() {
        AllGroupChats repo = mock(AllGroupChats.class);
        AuthService auth = mock(AuthService.class);
        JwtUtil jwt = mock(JwtUtil.class);
        CreateJoinGCController controller = new CreateJoinGCController(repo, auth, jwt);

        // setup data
        GCInfo gc = new GCInfo();
        gc.setExpectedRoomieCount(3);
        gc.setInviteCode(123456);
        gc.setUnlocked(false);
        // add two people already in
        gc.addToCurrentRoomieCount();
        gc.addToCurrentRoomieCount();

        User user = new User();
        user.setEmail("test@example.com");
        user.setGroupChatCode(0);

        when(jwt.extractEmail("token123")).thenReturn("test@example.com");
        when(auth.findByEmail("test@example.com")).thenReturn(user);
        when(repo.findByInviteCode(123456)).thenReturn(Optional.of(gc));

        // join
        ResponseEntity<GCInfo> response = controller.join(123456, "Bearer token123");

        // check that roommie count increased
        assertEquals(3, response.getBody().getCurrentRoomieCount());
    }

    @Test
    public void testJoiningOldGC() {
        // fake
        AllGroupChats repo = mock(AllGroupChats.class);
        AuthService auth = mock(AuthService.class);
        JwtUtil jwt = mock(JwtUtil.class);
        CreateJoinGCController controller = new CreateJoinGCController(repo, auth, jwt);

        // make fake data for a full GC
        GCInfo gc = new GCInfo();
        gc.setExpectedRoomieCount(3);
        gc.setInviteCode(654321);
        gc.setUnlocked(true);
        // alr full
        gc.addToCurrentRoomieCount();
        gc.addToCurrentRoomieCount();
        gc.addToCurrentRoomieCount();

        User user = new User();
        user.setEmail("olduser@example.com");
        user.setGroupChatCode(0);

        when(jwt.extractEmail("oldtoken")).thenReturn("olduser@example.com");
        when(auth.findByEmail("olduser@example.com")).thenReturn(user);
        when(repo.findByInviteCode(654321)).thenReturn(Optional.of(gc));

        ResponseEntity<GCInfo> response = controller.join(654321, "Bearer oldtoken");

        // checks that roommie count didnt change
        assertEquals(3, response.getBody().getCurrentRoomieCount());
    }
}
