package test;

import common.model.Chat;
import common.model.ChatMessage;
import common.model.User;
import common.ChatService;

import java.rmi.Naming;
import java.time.LocalDateTime;
import java.util.List;

public class TestChatMessaging {
    public static void main(String[] args) {
        try {
            // Connect to the remote ChatService
            ChatService chatService = (ChatService) Naming.lookup("rmi://localhost/ChatService");

            // Create a user (assume this user already exists in the database)
            User sender = new User();
            sender.setId(1); // Replace with valid user ID

            // Create a chat (assume this chat already exists in the database)
            Chat chat = new Chat();
            chat.setId(1); // Replace with valid chat ID

            // Send a new message
            ChatMessage message = new ChatMessage();
            message.setChat(chat);               // ✅ set Chat entity
            message.setSender(sender);             // ✅ set User entity
            message.setContent("Hello from test!");
            message.setTimestamp(LocalDateTime.now());

            chatService.sendMessage(message);
            System.out.println("Message sent successfully!");

            // Retrieve messages from the chat
            List<ChatMessage> messages = chatService.getMessagesByChatId(1); // same chatId
            System.out.println("Messages in chat ID 1:");
            for (ChatMessage msg : messages) {
                System.out.println(msg.getSender().getUsername() + ": " + msg.getContent());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
