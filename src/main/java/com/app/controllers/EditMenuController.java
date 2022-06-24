package com.app.controllers;

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

import static com.app.controllers.AuthController.openNewWindow;

public class EditMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private Button changeButton;

    @FXML
    private TableView<Quote> dataTable;

    @FXML
    private TableColumn<Quote, String> date;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<Quote, String> lecturer;

    @FXML
    private TableColumn<Quote, String> quote;

    @FXML
    private TableColumn<Quote, String> subject;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() {
        quote.setCellValueFactory(new PropertyValueFactory<>("quote"));
        lecturer.setCellValueFactory(new PropertyValueFactory<>("lecturer"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        ObservableList<Quote> data = FXCollections.observableArrayList(Quote.all());
        dataTable.setItems(data);

        exitButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/authorization-view.fxml", exitButton);
        });
    }

}

