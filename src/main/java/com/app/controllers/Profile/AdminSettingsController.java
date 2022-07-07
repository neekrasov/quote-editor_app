package com.app.controllers.Profile;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.app.controllers.Auth.AuthController;
import com.app.database.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminSettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;

    @FXML
    private Button changeButton;

    @FXML
    private TableView<User> dataTable;

    @FXML
    private TableColumn<User, String> user;

    @FXML
    void initialize() {
        loadTable(user, dataTable);

        changeButton.setOnAction(actionEvent -> {
            changeButton.getScene().getWindow().hide();


            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/user_settings-view.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();

            stage.setScene(new Scene(root));
            UserSettingsController controller = loader.getController();
            try {
                controller.setSelectedUser(getSelectedUser());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            stage.show();
        });

        backButton.setOnAction(actionEvent -> {
            backButton.getScene().getWindow().hide();
        });

    }

    private void loadTable(TableColumn<User, String> user, TableView<User> dataTable) {
        user.setCellValueFactory(new PropertyValueFactory<>("login"));
        ObservableList<User> data = FXCollections.observableArrayList(User.all());
        dataTable.setItems(data);
    }

    public User getSelectedUser() {
        return dataTable.getSelectionModel().getSelectedItem();
    }

}
