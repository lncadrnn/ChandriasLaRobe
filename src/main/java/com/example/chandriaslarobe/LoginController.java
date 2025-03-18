package com.example.chandriaslarobe;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.EventObject;


public class LoginController {

    @FXML
    private Label loginMessageLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;


    public void loginButtonOnAction(ActionEvent event) {

        if (usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false) {
            validateLogin();
        } else {
            loginMessageLabel.setText("Please enter username and password.");
        }
    }

    public void registerButtonOnAction(ActionEvent event) {
        createAccountForm(event);
    }

    public void validateLogin() {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + enterPasswordField.getText() + "'";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()) {
                if (queryResult.getInt(1) == 1) {
                    loginMessageLabel.setText("You are logged in.");
                    loadDashboard();
                } else {
                    loginMessageLabel.setText("Invalid username or password.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

    }

    public void applyCSS(Scene scene) {
        scene.getStylesheets().add(getClass().getResource("/com/example/chandriaslarobe/styles.css").toExternalForm());
    }

    public void createAccountForm(ActionEvent event) {
        try {
            Parent registerRoot = FXMLLoader.load(getClass().getResource("register.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(registerRoot, 600, 532));
            stage.setTitle("Sign Up");
            stage.show();
            stage.centerOnScreen();
            applyCSS(stage.getScene());

        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void loadDashboard() {
        try {
            Parent dashboardRoot = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
            // gineget ang scene na belong sa usernameTextField

            Stage stage = (Stage) usernameTextField.getScene().getWindow();
            stage.setScene(new Scene(dashboardRoot, 1000, 600));
            stage.centerOnScreen();
            stage.show();
            // applyCSS(stage.getScene());
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }
    }
}