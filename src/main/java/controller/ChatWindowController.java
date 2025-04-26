package controller;

import common.ChatClient;
import common.ChatService;
import common.model.Chat;
import common.model.ChatMessage;
import common.model.User;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import rmi.ClientImpl;

import java.rmi.Naming;
import java.time.LocalDateTime;

public class ChatWindowController {

    @FXML
    private ScrollPane messageScrollPane;

    @FXML
    private VBox messageVBox;

    @FXML
    private TextField messageTextField;

    @FXML
    private Button sendButton;

    private ChatService chatService;
    private ChatClient client;

    // These would typically be passed or set after login/chat selection
    private User currentUser;
    private Chat currentChat;

    @FXML
    public void initialize() {

        messageVBox.heightProperty().addListener((obs, oldVal, newVal) -> {
            messageScrollPane.setVvalue(1.0);
        });

        sendButton.setOnAction(event -> sendMessage());
        messageTextField.setOnAction(event -> sendMessage());

        try {
            chatService = (ChatService) Naming.lookup("rmi://localhost:1099/ChatService");
            client = new ClientImpl(this);
            chatService.registerClient(client); // Register client after successful setup
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {

        System.out.println("button clicked");

        String messageText = messageTextField.getText().trim();

        System.out.println("Message text: " + messageText);
        System.out.println("Current User: " + currentUser);
        System.out.println("Current Chat: " + currentChat);

        if (!messageText.isEmpty() && currentUser != null && currentChat != null) {
            ChatMessage message = new ChatMessage();
            message.setContent(messageText);
            message.setTimestamp(LocalDateTime.now());

            // Set only IDs, not whole objects (attach them manually)
            User senderRef = new User();
            senderRef.setId(currentUser.getId());
            message.setSender(senderRef);

            Chat chatRef = new Chat();
            chatRef.setId(currentChat.getId());
            message.setChat(chatRef);

            try {
                chatService.sendMessage(message); // Send to server
                //addMessage(message);              // Display immediately
                messageTextField.clear();          // Clear textbox
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void addMessage(ChatMessage message) {
        Platform.runLater(() -> {
            String displayText = message.getSender().getNickname() + ": " + message.getContent();
            Text text = new Text(displayText);
            TextFlow messageBubble = new TextFlow(text);
            messageBubble.getStyleClass().add("user-message");

            messageVBox.getChildren().add(messageBubble);
        });
    }

    // Set the current user from outside (after login/setup)
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    // Set the current chat
    public void setCurrentChat(Chat chat) {
        this.currentChat = chat;
    }
}
