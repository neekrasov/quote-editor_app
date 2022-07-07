package com.app.controllers.Profile;

import com.app.database.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AddUserToGroupController {
    TableView<User> parentTable;
    User verifierUser;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button backButton;

    @FXML
    private TableView<User> dataTable;

    @FXML
    private TableColumn<User, String> user;

    @FXML
    void initialize() {
        loadTable(user, dataTable);

        addButton.setOnAction(actionEvent -> {
            addButton.getScene().getWindow().hide();
            verifierUser.setUserToGroup(getSelectedUser());
            parentTable.getItems().add(getSelectedUser());
            dataTable.refresh();
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

    private User getSelectedUser() {
        return dataTable.getSelectionModel().getSelectedItem();
    }

    public void setTable(TableView<User> dataTable) {
        this.parentTable = dataTable;
    }

    public void setUser(User verifierUser) {
        this.verifierUser = verifierUser;
    }
}
