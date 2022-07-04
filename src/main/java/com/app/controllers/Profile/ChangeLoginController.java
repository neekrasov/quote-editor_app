package com.app.controllers.Profile;

import com.app.animations.Shake;
import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

import static com.app.controllers.Auth.AuthController.openNewWindow;

public class ChangeLoginController {

    User user;
    UserProfileController userProfileController;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button changeButton;

    @FXML
    private Button exitButton;

    @FXML
    private TextField newLoginField;

    @FXML
    void initialize() {
        changeButton.setOnAction(actionEvent -> {
            String login = newLoginField.getText();
            User anotherUser = User.get(login);
            if (anotherUser!=null || newLoginField.getText().equals("")) {
                Shake button = new Shake(newLoginField);
                button.play();
                return;
            }
            User.updateLogin(user.getId(), login);

            user.setLogin(login);
            userProfileController.setUser(user);

            changeButton.getScene().getWindow().hide();
        });

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
        });
    }

    public void setUser(User user) {
        this.user = user;
        newLoginField.setText(user.getLogin());
    }
    public void setParent(UserProfileController controller) {
        this.userProfileController = controller;
    }

    public void goBack(){
        FXMLLoader loader = openNewWindow("com/app/user_profile-view.fxml", exitButton, true);
        UserProfileController userProfileController = loader.getController();
        userProfileController.setUser(user);
    }
}
