package com.app.controllers.Profile;

import com.app.controllers.EditQuotes.EditMenuController;
import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

import static com.app.controllers.Auth.AuthController.openNewWindow;

public class UserProfileController {
    private User user;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button changeLoginButton;

    @FXML
    private Button changePassButton;

    @FXML
    private Label labelHello;

    @FXML
    void initialize() {
        backButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/edit_menu-view.fxml", backButton, true);
            EditMenuController editMenuController = loader.getController();
            editMenuController.setUser(user);
        });

        changeLoginButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/change_user_login-view.fxml",changeLoginButton,false);
            ChangeLoginController changeLoginController = loader.getController();
            changeLoginController.setUser(user);
            changeLoginController.setParent(this);

        });
        changePassButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/change_user_password-view.fxml",changePassButton,false);
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.setUser(user);
        });
    }

    public void setUser(User user) {
        this.user = user;
        labelHello.setText("Hello "+ user.getLogin() + "!");
    }



}
