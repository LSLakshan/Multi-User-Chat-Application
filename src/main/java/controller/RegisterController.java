package controller;

import common.model.User;
import common.dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField nicknameField;
    @FXML private TextField emailField;
    @FXML private Label statusLabel;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void handleSignup() {
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();
        String nickname = nicknameField.getText().trim();

        if (username.isEmpty() || password.isEmpty() || nickname.isEmpty() || email.isEmpty()) {
            statusLabel.setText("Please fill all fields.");
            return;
        }

        if (!isValidInput(username, password, nickname, email)) {
            statusLabel.setText("Invalid input. Fields must be 3-20 characters, username alphanumeric.");
            return;
        }


        try {
            boolean success = userDAO.register(username, password, nickname, email);
            if (success) {
                statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                statusLabel.setText("Registration successful! Go login.");
                clearFields();
                goToLogin();
            } else {
                statusLabel.setText("Username already exists.");
            }
        } catch (Exception e) {
            statusLabel.setText("An error occurred. Please try again.");
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Login");
        } catch (Exception e) {
            statusLabel.setText("Failed to load login page.");
        }
    }

    private boolean isValidInput(String username, String password, String nickname, String email) {
        return username.length() >= 3 && username.length() <= 20 &&
                password.length() >= 3 && password.length() <= 20 &&
                nickname.length() >= 3 && nickname.length() <= 20 &&
                username.matches("[a-zA-Z0-9]+") &&
                email.endsWith("@gmail.com");
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        nicknameField.clear();
        emailField.clear();
    }
}