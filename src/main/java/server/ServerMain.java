package server;

import common.ChatService;
import rmi.ChatServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerMain {
    public static void main(String[] args) {
        try {
            // Start the RMI registry on port 1099
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry started on port 1099");

            // Create service implementation
            ChatService chatService = new ChatServiceImpl();

            // Bind the service to a name
            Naming.rebind("ChatService", chatService);
            System.out.println("ChatService is bound and ready.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
