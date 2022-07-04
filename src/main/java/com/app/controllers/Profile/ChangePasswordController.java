package com.app.controllers.Profile;

import com.app.animations.Shake;
import com.app.database.models.User;
import com.app.services.HashPasswordMD5;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChangePasswordController {

    User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button changeButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField newPasswordField;

    @FXML
    private TextField newPasswordField2;

    @FXML
    private TextField oldPasswordField;

    @FXML
    void initialize() {
        changeButton.setOnAction(actionEvent -> {
            String oldPassword = oldPasswordField.getText();
            if (!Objects.equals(HashPasswordMD5.hash_password(oldPassword), user.getPassword()) || oldPassword.equals("")) {
                new Shake(oldPasswordField).play();
                return;
            }
            String newPassword1 = newPasswordField.getText();
            String newPassword2 = newPasswordField2.getText();

            if (!newPassword1.equals("") && !newPassword2.equals("") && newPassword1.equals(newPassword2)) {
                User.updatePassword(user.getId(), newPassword1);
                changeButton.getScene().getWindow().hide();
            } else {
                new Shake(newPasswordField).play();
                new Shake(newPasswordField2).play();
            }
        });
    }

    public void setUser(User user) {
        this.user = user;
    }

}
