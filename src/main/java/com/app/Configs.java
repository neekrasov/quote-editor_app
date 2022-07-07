package com.app;

import com.app.controllers.Auth.AuthController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class Configs {
    protected static String dbHost = "std-mysql";
    protected static String dbPort = "3306";
    protected static String dbName = "std_1969_quote_editor";
    protected static String dbUser = "std_1969_quote_editor";
    protected static String dbPassword = "123456789";

    public static FXMLLoader openNewWindow(String window, Button button, boolean hide) {

        if (hide) {
            button.getScene().getWindow().hide();
        } else {
            button.getScene().getWindow();
        }


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AuthController.class.getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();

        stage.setScene(new Scene(root));
        stage.show();

        return loader;
    }
}
