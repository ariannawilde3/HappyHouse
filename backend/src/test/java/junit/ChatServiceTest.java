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

    //passes
    @Test
    public void testSendMessage() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));
        when(repo.save(any(Chat.class))).thenReturn(chat);

        ChatService service = new ChatService(repo);
        User sender = new User();  // Assume User has default constructor
        String content = "Hello";
        Message msg = service.sendMessage(chatId, sender, content);

        assertNotNull(msg);
        assertEquals(content, msg.getContent());
        assertEquals(1, chat.getMessages().size());
        verify(repo).save(chat);
    }

    //passes
    @Test(expected = RuntimeException.class)
    public void testGetMessagesChatNotFound() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        when(repo.findById(chatId)).thenReturn(Optional.empty());

        ChatService service = new ChatService(repo);
        service.getMessages(chatId);
    }

    // Negative Test 1: Send message with null content
    @Test(expected = IllegalArgumentException.class)
    public void testSendMessageWithNullContent() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));

        ChatService service = new ChatService(repo);
        User sender = new User();
        // Sending null content should throw IllegalArgumentException
        service.sendMessage(chatId, sender, null);
    }
}