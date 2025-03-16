package com.example.chandriaslarobe;

import com.example.chandriaslarobe.DatabaseConnection;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RegisterController {

    @FXML
    private Button backToLoginButton;
    @FXML
    private Label registrationMessageLabel;
    @FXML
    private Label confirmPasswordLabel;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField usernameTextField;

    public void registerButtonOnAction(ActionEvent event) {
        if (setPasswordField.getText().equals(confirmPasswordField.getText())) {
            registerUser();
            confirmPasswordLabel.setText("");
        } else {
            confirmPasswordLabel.setText("Passwords do not match.");
        }
    }

    public void applyCSS(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("/com/example/chandriaslarobe/styles.css").toExternalForm());
    }

    public void backToLoginButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Stage registerStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        registerStage.setTitle("Login");
        registerStage.setResizable(false);
        registerStage.setScene(scene);
        registerStage.show();
        registerStage.centerOnScreen();
        applyCSS(scene);
    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstNameTextField.getText().trim();
        String lastname = lastNameTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = setPasswordField.getText().trim();

        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || password.isEmpty()) {
            registrationMessageLabel.setText("All fields are required!");
            return;
        }

        if (isUsernameTaken(username, connectDB)) {
            registrationMessageLabel.setText("Username already exists! Please choose another.");
            return;
        }

        String insertQuery = "INSERT INTO user_account (firstname, lastname, username, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery)) {
            preparedStatement.setString(1, firstname);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();

            registrationMessageLabel.setText("User has been registered successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean isUsernameTaken(String username, Connection connectDB) {
        String checkQuery = "SELECT COUNT(*) FROM user_account WHERE username = ?";

        try (PreparedStatement preparedStatement = connectDB.prepareStatement(checkQuery)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
