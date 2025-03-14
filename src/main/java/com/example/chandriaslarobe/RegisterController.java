package com.example.chandriaslarobe;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {

    @FXML
    private Button closeButton;
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
            confirmPasswordLabel.setText("Password do not match.");

        }
    }

    public void closeButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void registerUser() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstNameTextField.getText();
        String lastname = lastNameTextField.getText();
        String username = usernameTextField.getText();
        String password = setPasswordField.getText();

        String InsertFields = "INSERT INTO user_account(lastname, firstname, username, password) VALUES ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "','" + password + "'";
        String insertToRegister = InsertFields + insertValues;

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            registrationMessageLabel.setText("User has been registered successfully.");

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}
