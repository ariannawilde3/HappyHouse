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

    // Loop Testing: Testing the messages list additions (as if loop body per add)
    //passes
    @Test
    public void testMessagesLoopZero() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        assertEquals(0, chat.getMessages().size());  // Loop body not executed
    }

    //passes
    @Test
    public void testMessagesLoopOne() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        Message msg = new Message(new User(), "Message 1");
        chat.addMessage(msg);
        assertEquals(1, chat.getMessages().size());  // Loop body executed once
    }

    //passes
    @Test
    public void testMessagesLoopTwo() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        chat.addMessage(new Message(new User(), "Message 1"));
        chat.addMessage(new Message(new User(), "Message 2"));
        assertEquals(2, chat.getMessages().size());  // Loop body executed twice (and typical)
    }

    // Loop Testing: Testing the members list additions (as if loop body per add)
    //passes
    @Test
    public void testMembersLoopZero() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        assertEquals(0, chat.getMembers().size());  // Loop body not executed
    }

    //passes
    @Test
    public void testMembersLoopOne() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        User user = new User();
        chat.getMembers().add(user);  // Direct add since no addMember method
        assertEquals(1, chat.getMembers().size());  // Loop body executed once
    }

    //passes
    @Test
    public void testMembersLoopTwo() {
        Chat chat = new Chat("1", "Test", new ArrayList<>(), new ArrayList<>());
        chat.getMembers().add(new User());
        chat.getMembers().add(new User());
        assertEquals(2, chat.getMembers().size());  // Loop body executed twice (and typical)
    }

    // Acceptance Testing: Black-box test verifying user requirement (create chat, send message, retrieve it)
    //passes
    @Test
    public void testAcceptanceSendAndGetMessage() {
        ChatRepository repo = mock(ChatRepository.class);
        String chatId = "1";
        Chat chat = new Chat(chatId, "Test Chat", new ArrayList<>(), new ArrayList<>());
        when(repo.save(any(Chat.class))).thenReturn(chat);
        when(repo.findById(chatId)).thenReturn(Optional.of(chat));

        ChatService service = new ChatService(repo);
        service.saveChat(chat);  // Simulate creating chat

        User sender = new User();
        String content = "Acceptance test message";
        Message sentMsg = service.sendMessage(chatId, sender, content);

        List<Message> retrieved = service.getMessages(chatId);
        assertEquals(1, retrieved.size());
        assertEquals(content, retrieved.get(0).getContent());
    }
}