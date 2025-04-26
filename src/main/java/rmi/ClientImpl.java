package rmi;

import controller.ChatWindowController;
import common.ChatClient;
import common.model.ChatMessage;
import javafx.application.Platform;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements ChatClient {

    private final ChatWindowController controller;

    public ClientImpl(ChatWindowController controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void receiveMessage(ChatMessage message) throws RemoteException {
        // Use Platform.runLater to update JavaFX UI from RMI thread
        Platform.runLater(() -> {
            // Assuming `addMessage` in ChatWindowController takes ChatMessage as input
            controller.addMessage(message);
        });
    }
}
