package junit;

import com.happyhouse.controller.PollsController;
import com.happyhouse.dto.PollRequest;
import com.happyhouse.model.Poll;
import com.happyhouse.repository.PollsRepository;
import com.happyhouse.service.AuthService;
import com.happyhouse.util.JwtUtil;

import org.junit.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestPolls {

    @Test
    public void testCreatingPoll() {
        // fake dependencies
        PollsRepository repo = mock(PollsRepository.class);
        AuthService auth = mock(AuthService.class);
        JwtUtil jwt = mock(JwtUtil.class);

        PollsController controller = new PollsController(repo, auth, jwt);

        PollRequest req = new PollRequest() {
            @Override public String getTitle() { return "Best dinner?"; }
            @Override public String getVoteOption1() { return "Pizza"; }
            @Override public String getVoteOption2() { return "Tacos"; }
        };

        final String bearer = "Bearer token123";
        final String token = "token123";
        final String email = "creator@example.com";

        when(jwt.extractEmail(token)).thenReturn(email);

        var creator = mock(com.happyhouse.model.User.class);
        when(creator.getGroupChatCode()).thenReturn(42);
        when(auth.findByEmail(email)).thenReturn(creator);

        // get poll
        AtomicReference<Poll> savedRef = new AtomicReference<>();
        when(repo.save(any(Poll.class))).thenAnswer(inv -> {
            Poll p = inv.getArgument(0);
            savedRef.set(p);
            return p;
        });

        controller.createPoll(req, bearer);

        Poll saved = savedRef.get();
        assertNotNull("Poll should be saved", saved);
        assertEquals("Best dinner?", saved.getTitle());
        assertEquals("Pizza", saved.getVoteOption1());
        assertEquals("Tacos", saved.getVoteOption2());
        assertTrue(saved.getVoters().isEmpty());
    }

    
    @Test
    public void testVotingInPoll() {
        // fake dependencies
        PollsRepository repo = mock(PollsRepository.class);
        AuthService auth = mock(AuthService.class);
        JwtUtil jwt = mock(JwtUtil.class);
        PollsController controller = new PollsController(repo, auth, jwt);

        // make poll
        Poll poll = new Poll("Lunch choice?", "Salad", "Burger", "creator@example.com", 7);
        when(repo.findById("poll-1")).thenReturn(Optional.of(poll));
        when(repo.save(any(Poll.class))).thenAnswer(inv -> inv.getArgument(0));

        // Auth/JWT
        final String token = "votertoken";
        final String voter = "voter@example.com";
        when(jwt.extractEmail(token)).thenReturn(voter);

        // vote opt 1
        PollsController.VoteRequest vr = new PollsController.VoteRequest();
        vr.setOption(1);

        // call controller
        controller.vote("Bearer " + token, "poll-1", vr);

        assertEquals(1, poll.getTotalVotes());
        assertEquals(1, poll.getVotesFor1());
        assertEquals(0, poll.getVotesFor2());
        assertTrue(poll.getVoters().contains(voter));
    }


}
