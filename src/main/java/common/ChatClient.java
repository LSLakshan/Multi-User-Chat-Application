package common;

import common.model.ChatMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI callback interface for pushing messages to clients.
 */
public interface ChatClient extends Remote {
    /**
     * Called by the server to deliver a new message to the client.
     *
     * @param message The chat message to be delivered.
     * @throws RemoteException If a communication-related exception occurs.
     */
    void receiveMessage(ChatMessage message) throws RemoteException;
}
