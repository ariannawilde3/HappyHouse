package junit;

import com.happyhouse.model.PinnedMessage;
import com.happyhouse.repository.PinnedMessageRepo;
import com.happyhouse.service.PinnedMessageService;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PinnedMessagesTest {

    @Test
    public void testPinMessage() {
        PinnedMessageRepo pinnedMessageRepo = mock(PinnedMessageRepo.class);

        PinnedMessage pinnedMsg = new PinnedMessage();
        pinnedMsg.setId("pinned123");
        pinnedMsg.setMessageId("message123");
        pinnedMsg.setHouseId("house123");
        pinnedMsg.setUsername("arianna");
        pinnedMsg.setContent("this is a pinned message");
        pinnedMsg.setTimestamp("2024-01-15T10:30:00");

        when(pinnedMessageRepo.findByMessageIdAndHouseId("message123", "house123"))
                .thenReturn(Optional.empty());
        when(pinnedMessageRepo.save(any(PinnedMessage.class))).thenReturn(pinnedMsg);

        PinnedMessageService pinnedMessageService = new PinnedMessageService(pinnedMessageRepo);
        PinnedMessage output = pinnedMessageService.pinMessage("message123", "house123", "arianna",
                "this is a pinned message", "2024-01-15T10:30:00");

        assertEquals("message123", output.getMessageId());
        assertEquals("house123", output.getHouseId());
    }

    @Test
    public void testPinMessageAlreadyPinned() {
        PinnedMessageRepo pinnedMessageRepo = mock(PinnedMessageRepo.class);

        PinnedMessage pinnedMsg = new PinnedMessage();
        pinnedMsg.setId("pinned123");
        pinnedMsg.setMessageId("message123");
        pinnedMsg.setHouseId("house123");
        pinnedMsg.setUsername("drew");
        pinnedMsg.setContent("this is a pinned message");
        pinnedMsg.setTimestamp("2024-01-15T10:30:00");

        when(pinnedMessageRepo.findByMessageIdAndHouseId("message123", "house123"))
                .thenReturn(Optional.of(pinnedMsg));

        PinnedMessageService pinnedMessageService = new PinnedMessageService(pinnedMessageRepo);
        PinnedMessage output = pinnedMessageService.pinMessage("message123", "house123", "arianna",
                "this is a pinned message", "2024-01-15T10:30:00");

        assertEquals("pinned123", output.getId());
        verify(pinnedMessageRepo, never()).save(any(PinnedMessage.class));
    }
}