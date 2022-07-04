package com.app.controllers.EditQuotes;

import java.net.URL;
import java.util.ResourceBundle;

import com.app.database.models.Quote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import static com.app.controllers.Auth.AuthController.openNewWindow;

public class GuestMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Quote> dataTable;

    @FXML
    private TableColumn<Quote, String> date;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<Quote, String> lecturer;

    @FXML
    private TableColumn<Quote, String> quote;
    @FXML
    private TableColumn<Quote, String> subject;

    @FXML
    void initialize() {
        loadTable();
        exitButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/authorization-view.fxml", exitButton, true);
        });
    }

    public void loadTable() {
        quote.setCellValueFactory(new PropertyValueFactory<>("quote"));
        lecturer.setCellValueFactory(new PropertyValueFactory<>("lecturer"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        ObservableList<Quote> data = FXCollections.observableArrayList(Quote.all());
        dataTable.setItems(data);
    }

}
