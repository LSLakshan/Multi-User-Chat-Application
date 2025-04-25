package com.chatapp.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ChatWindowController {

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField messageField;

    @FXML
    private Button sendButton;

    // Called automatically by JavaFX after the FXML loads
    @FXML
    public void initialize() {
        sendButton.setOnAction(event -> sendMessage());
    }

    private void sendMessage() {
        String message = messageField.getText().trim();
        if (!message.isEmpty()) {
            // This is where you'd send the message over RMI in your actual app
            chatArea.appendText("You: " + message + "\n");
            messageField.clear();
        }
    }

    // Call this method to display received messages from other users
    public void receiveMessage(String message) {
        chatArea.appendText(message + "\n");
    }
}
