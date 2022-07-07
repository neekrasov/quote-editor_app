package com.app.controllers.Profile;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.database.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import static com.app.Configs.openNewWindow;

public class VerifierGroupController {

    User verifierUser;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button backButton;
    @FXML
    private Button addButton;

    @FXML
    private TableView<User> dataTable;

    @FXML
    private TableColumn<User, String> user;

    @FXML
    private Label titleLabel;


    @FXML
    void initialize() {
        loadTable(user, dataTable);
        backButton.setOnAction(actionEvent -> {
            backButton.getScene().getWindow().hide();
        });
        addButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/add_user_group-view.fxml", addButton, false);
            AddUserToGroupController addUserToGroupController = loader.getController();
            addUserToGroupController.setTable(dataTable);
            addUserToGroupController.setUser(verifierUser);
        });
    }

     void loadTable(TableColumn<User, String> user, TableView<User> dataTable) {
        user.setCellValueFactory(new PropertyValueFactory<>("login"));
    }

    public void setUser(User user){
        this.verifierUser = user;
        ObservableList<User> data = FXCollections.observableArrayList(verifierUser.getGroup());
        dataTable.setItems(data);
    }



}
