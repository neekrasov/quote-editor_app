package com.app.controllers.Profile;

import com.app.controllers.Auth.AuthController;
import com.app.controllers.EditQuotes.EditMenuController;
import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static com.app.Configs.openNewWindow;

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
    private Label numberLabel;
    @FXML
    private Button adminSettingsButton;
    @FXML
    private Button myGroupButton;

    @FXML
    void initialize() {

        backButton.setOnAction(actionEvent -> {
            backButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/edit_menu-view.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();

            EditMenuController editMenuController = loader.getController();
            stage.setScene(new Scene(root));
            try {
                editMenuController.setUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            editMenuController.refreshTable();
            stage.show();
        });

        changeLoginButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/change_user_login-view.fxml", changeLoginButton, false);
            ChangeLoginController changeLoginController = loader.getController();
            changeLoginController.setUser(user);
            changeLoginController.setParent(this);

        });
        changePassButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/change_user_password-view.fxml", changePassButton, false);
            ChangePasswordController changePasswordController = loader.getController();
            changePasswordController.setUser(user);
        });

        adminSettingsButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/admin_settings-view.fxml", adminSettingsButton, false);
        });

        myGroupButton.setOnAction(actionEvent -> {
            changePassButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/verifier_group-view.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            VerifierGroupController verifierGroupController = loader.getController();
            verifierGroupController.setUser(user);
            stage.show();
        });
    }

    public void setUser(User user) throws SQLException {
        this.user = user;

        labelHello.setText("Hello " + user.getLogin() + "!");
        numberLabel.setText(String.valueOf(user.quotesCount()));

        if (!user.isStaff()) {
            adminSettingsButton.setVisible(false);
        }
        if (!user.isVerifier()){
            myGroupButton.setVisible(false);
        }
    }


}
