package rmi;

import common.ChatClient;
import common.ChatService;
import common.dao.ChatDAO;
import common.dao.ChatMessageDAO;
import common.dao.UserChatDAO;
import common.dao.UserDAO;
import common.model.Chat;
import common.model.ChatMessage;
import common.model.User;
import common.model.UserChat;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {

    private final UserDAO userDAO = new UserDAO();
    private final ChatDAO chatDAO = new ChatDAO();
    private final ChatMessageDAO chatMessageDAO = new ChatMessageDAO();
    private final UserChatDAO userChatDAO = new UserChatDAO();

    // Thread-safe list for client callbacks
    private final List<ChatClient> connectedClients = new CopyOnWriteArrayList<>();

    public ChatServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public User registerUser(User user) throws RemoteException {
        userDAO.saveUser(user);
        return user;
    }

    @Override
    public User loginUser(String email, String password) throws RemoteException {
        User user = userDAO.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public Chat startChat(List<User> participants) throws RemoteException {
        Chat chat = new Chat();
        chatDAO.saveChat(chat);

        for (User user : participants) {
            UserChat userChat = new UserChat();
            userChat.setChatId(chat);
            userChat.setUserId(user);
            userChatDAO.saveUserChat(userChat);
        }
        return chat;
    }

    @Override
    public void sendMessage(ChatMessage message) throws RemoteException {
        chatMessageDAO.saveMessage(message);
        broadcastMessage(message); // now passing the full object
    }

    @Override
    public List<ChatMessage> getMessagesByChatId(int chatId) throws RemoteException {
        return chatMessageDAO.getMessagesByChatId(chatId);
    }

    @Override
    public List<Chat> getUserChats(int userId) throws RemoteException {
        return userChatDAO.getUserChatsByUserId(userId)
                .stream()
                .map(UserChat::getChatId)
                .toList();
    }

    @Override
    public void broadcastMessage(ChatMessage message) throws RemoteException {
        for (ChatClient client : connectedClients) {
            try {
                client.receiveMessage(message);
            } catch (RemoteException e) {
                System.out.println("Failed to send message to a client. Removing...");
                connectedClients.remove(client);
            }
        }
    }

    @Override
    public void registerClient(ChatClient client) throws RemoteException {
        if (!connectedClients.contains(client)) {
            connectedClients.add(client);
            System.out.println("Client registered: " + client);
        }
    }

    @Override
    public void unregisterClient(ChatClient client) throws RemoteException {
        connectedClients.remove(client);
        System.out.println("Client unregistered: " + client);
    }
}
