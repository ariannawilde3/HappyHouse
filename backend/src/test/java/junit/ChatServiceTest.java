package junit;

import com.happyhouse.model.Chat;
import com.happyhouse.model.Message;
import com.happyhouse.model.User;
import com.happyhouse.repository.ChatRepository;
import com.happyhouse.service.ChatService;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChatServiceTest {

    // Unit Test 1: Verify message is created with correct content
    @Test
    public void testSendMessageContent() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));
        when(repo.save(any(Chat.class))).thenReturn(chat);

        ChatService service = new ChatService(repo);
        User sender = new User();
        String content = "Hello";
        Message msg = service.sendMessage(chatId, sender, content);

        assertEquals(content, msg.getContent());
    }

    // Unit Test 2: Verify message is added to chat
    @Test
    public void testSendMessageAddsToChat() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));
        when(repo.save(any(Chat.class))).thenReturn(chat);

        ChatService service = new ChatService(repo);
        User sender = new User();
        String content = "Hello";
        service.sendMessage(chatId, sender, content);

        assertEquals(1, chat.getMessages().size());
    }

    // Unit Test 3: Verify repository save is called
    @Test
    public void testSendMessageSavesChat() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));
        when(repo.save(any(Chat.class))).thenReturn(chat);

        ChatService service = new ChatService(repo);
        User sender = new User();
        String content = "Hello";
        Message result = service.sendMessage(chatId, sender, content);

        assertNotNull(result);
        verify(repo).save(chat);
    }

    // Unit Test 4: Verify exception when chat not found
    @Test
    public void testGetMessagesChatNotFound() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        when(repo.findById(chatId)).thenReturn(Optional.empty());

        ChatService service = new ChatService(repo);

        try {
            service.getMessages(chatId);
            fail("Expected RuntimeException to be thrown");
        } catch (RuntimeException e) {
            assertNotNull(e);
        }
    }

    // Negative Test: Send message with null content
    @Test
    public void testSendMessageWithNullContent() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));

        ChatService service = new ChatService(repo);
        User sender = new User();

        try {
            service.sendMessage(chatId, sender, null);
            fail("Expected IllegalArgumentException to be thrown");
        } catch (IllegalArgumentException e) {
            assertNotNull(e);
        }
    }
}