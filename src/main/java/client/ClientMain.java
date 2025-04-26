package client;

import common.model.User;
import common.ChatService;

import java.rmi.Naming;

public class ClientMain {
    public static void main(String[] args) {
        try {
            // Lookup the remote service
            ChatService chatService = (ChatService) Naming.lookup("rmi://localhost/ChatService");
            System.out.println("Connected to ChatService");

            // Example: Register a new user
            User user = new User();
            user.setUsername("testuser");
            user.setEmail("test13@example.com");
            user.setPassword("1234");
            user.setRole(User.Role.USER);
            chatService.registerUser(user);

            System.out.println("User registered successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
