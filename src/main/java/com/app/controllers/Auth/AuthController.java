package com.app.controllers.Auth;

import com.app.animations.Shake;
import com.app.controllers.EditQuotes.EditMenuController;
import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.app.Configs.openNewWindow;

public class AuthController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signGuestButton;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        signInButton.setOnAction(actionEvent -> {
            try {
                String login = loginField.getText().trim();
                String password = passwordField.getText().trim();
                loginUser(login, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        signUpButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/registration-view.fxml", signUpButton, true);
        });
        signGuestButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/guest_menu-view.fxml", signGuestButton, true);
        });

    }

    private void loginUser(String login, String password) throws SQLException {
        if (!login.equals("") && !password.equals("")) {
            User user = User.get(login, password);
            if (user != null) {
                signInButton.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(AuthController.class.getResource("/com/app/edit_menu-view.fxml"));

                try {
                    loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();

                EditMenuController editMenuController = loader.getController();
                editMenuController.setUser(user);
                editMenuController.setCounter(user.quotesCount());

                stage.setScene(new Scene(root));
                stage.show();
            } else loginErrorAnimation();
        } else loginErrorAnimation();

    }

    private void loginErrorAnimation() {
        new Shake(loginField).play();
        new Shake(passwordField).play();
    }


}
