package com.app.controllers.Auth;

import com.app.animations.Shake;
import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static com.app.Configs.openNewWindow;

public class RegistrationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField1;

    @FXML
    private PasswordField passwordField2;

    @FXML
    private Button signGuestButton;

    @FXML
    private Button signUpButton;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
            boolean status = signUp();
            if (!status) {
                registerErrorAnimation();
            } else {
                openNewWindow("/com/app/authorization-view.fxml", signUpButton, true);
            }
        });

        backButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/authorization-view.fxml", backButton, true);
        });


        signGuestButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/edit_menu-view.fxml", signGuestButton, true);
        });
    }

    private boolean signUp() {
        String login = loginField.getText().trim();
        String password1 = passwordField1.getText().trim();
        String password2 = passwordField2.getText().trim();

        if (!login.equals("") && !password1.equals("") && !password2.equals("") && password1.equals(password2)) {
            User newUser = User.register(login, password2);
            if (newUser != null) {
                ArrayList<Integer> defaultFunctions = new ArrayList<>();
                defaultFunctions.add(1);
                defaultFunctions.add(5);
                defaultFunctions.add(6);
                newUser.setFunctions(defaultFunctions);
                return true;
            } else return false;
        } else {
            return false;
        }
    }

    private void registerErrorAnimation() {
        Shake userLoginAnimation = new Shake(loginField);
        Shake userPassword1Animation = new Shake(passwordField1);
        Shake userPassword2Animation = new Shake(passwordField2);
        userLoginAnimation.play();
        userPassword1Animation.play();
        userPassword2Animation.play();
    }
}
