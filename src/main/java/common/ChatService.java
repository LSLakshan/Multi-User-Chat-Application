package rmi;

import rmi.ChatClient;
import common.model.Chat;
import common.model.ChatMessage;
import common.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {
    // User operations
    User registerUser(User user) throws RemoteException;
    User loginUser(String email, String password) throws RemoteException;

    // Chat operations
    Chat startChat(List<User> participants) throws RemoteException;
    void sendMessage(ChatMessage message) throws RemoteException;
    List<ChatMessage> getMessagesByChatId(int chatId) throws RemoteException;
    List<Chat> getUserChats(int userId) throws RemoteException;

    // Broadcast to all (used for public chatrooms)
    void broadcastMessage(String message) throws RemoteException;

    // Client connection
    void registerClient(ChatClient client) throws RemoteException;
    void unregisterClient(ChatClient client) throws RemoteException;
}
