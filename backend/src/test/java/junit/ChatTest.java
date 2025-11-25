package junit;

import com.happyhouse.model.Chat;
import com.happyhouse.model.Message;
import com.happyhouse.model.User;
import com.happyhouse.repository.ChatRepository;
import com.happyhouse.service.ChatService;
import org.junit.Test;
import java.util.List;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChatTest {


    @Test
    public void testMessagesZero() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        assertEquals(0, chat.getMessages().size());
    }

    @Test
    public void testMessagesOne() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        Message msg = new Message(new User(), "Message 1");
        chat.addMessage(msg);
        assertEquals(1, chat.getMessages().size());
    }

    @Test
    public void testMessagesTwo() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        Message msg1 = new Message(new User(), "Message 1");
        Message msg2 = new Message(new User(), "Message 2");
        chat.addMessage(msg1);
        chat.addMessage(msg2);
        assertEquals(2, chat.getMessages().size());
    }

    @Test
    public void testMembersZero() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        assertEquals(0, chat.getMembers().size());
    }

    @Test
    public void testMembersOne() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        User user = new User();
        chat.getMembers().add(user);
        assertEquals(1, chat.getMembers().size());
    }

    @Test
    public void testMembers9Two() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        User user1 = new User();
        User user2 = new User();
        chat.getMembers().add(user1);
        chat.getMembers().add(user2);
        assertEquals(2, chat.getMembers().size());
    }

    // Acceptance Test: Verify message content after sending
    @Test
    public void testAcceptanceSendMessageContent() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.save(any(Chat.class))).thenReturn(chat);
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));

        ChatService service = new ChatService(repo);
        service.saveChat(chat);

        User sender = new User();
        String content = "Acceptance test message";
        Message sentMsg = service.sendMessage(chatId, sender, content);

        assertEquals(content, sentMsg.getContent());
    }

    // Acceptance Test: Verify message sender after sending
    @Test
    public void testAcceptanceSendMessageSender() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.save(any(Chat.class))).thenReturn(chat);
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));

        ChatService service = new ChatService(repo);
        service.saveChat(chat);

        User sender = new User();
        String content = "Acceptance test message";
        Message sentMsg = service.sendMessage(chatId, sender, content);

        assertEquals(sender, sentMsg.getSender());
    }

    // Acceptance Test: Verify message can be retrieved after sending
    @Test
    public void testAcceptanceRetrieveMessage() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.save(any(Chat.class))).thenReturn(chat);
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));

        ChatService service = new ChatService(repo);
        service.saveChat(chat);

        User sender = new User();
        String content = "Acceptance test message";
        service.sendMessage(chatId, sender, content);

        List<Message> retrieved = service.getMessages(chatId);
        assertEquals(1, retrieved.size());
    }

    // Acceptance Test: Verify retrieved message content matches sent message
    @Test
    public void testAcceptanceRetrievedMessageContent() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.save(any(Chat.class))).thenReturn(chat);
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));

        ChatService service = new ChatService(repo);
        service.saveChat(chat);

        User sender = new User();
        String content = "Acceptance test message";
        service.sendMessage(chatId, sender, content);

        List<Message> retrieved = service.getMessages(chatId);
        assertEquals(content, retrieved.get(0).getContent());
    }
}