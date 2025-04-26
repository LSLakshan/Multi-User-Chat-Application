package controller;

import common.model.User;
import common.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Please enter both username and password.");
            return;
        }

        if (!isValidInput(username, password)) {
            errorLabel.setText("Invalid input. Username and password must be 3-20 characters.");
            return;
        }

        try {
            User user = userDAO.login(username, password);
            if (user != null) {
                loadChatWindow(user);
            } else {
                errorLabel.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            errorLabel.setText("An error occurred. Please try again.");
        }
    }

    @FXML
    private void goToSignup() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Register.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Register");
        } catch (Exception e) {
            errorLabel.setText("Failed to load signup page.");
        }
    }

    private void loadChatWindow(User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ChatWindow.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(loader.load());

            ChatWindowController controller = loader.getController();
            controller.setCurrentUser(user);

            stage.setScene(scene);
            stage.setTitle("Chat - " + user.getNickname());
        } catch (Exception e) {
            errorLabel.setText("Failed to load chat window.");
        }
    }

    private boolean isValidInput(String username, String password) {
        return username.length() >= 3 && username.length() <= 20 &&
                password.length() >= 3 && password.length() <= 20 &&
                username.matches("[a-zA-Z0-9]+");
    }
}