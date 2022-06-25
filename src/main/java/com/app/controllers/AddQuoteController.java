package com.app.controllers;

import com.app.database.models.Quote;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddQuoteController {
    EditMenuController parent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addButton;

    @FXML
    private DatePicker dateField;

    @FXML
    private Button exitButton;

    @FXML
    private TextField lecturerNameField;

    @FXML
    private TextField quoteField;

    @FXML
    private TextField subjectField;

    @FXML
    void initialize() {
        dateField.setValue(LocalDate.now());

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
        });

        addButton.setOnAction((actionEvent -> {
            String quote = quoteField.getText();
            String lecturer = lecturerNameField.getText();
            String subject = subjectField.getText();
            String date = String.valueOf(dateField.getValue());

            Quote.create(quote, lecturer, subject,date);
            exitButton.getScene().getWindow().hide();
            parent.loadTable();
        }));
    }
    public void setParent(EditMenuController controller){
        this.parent = controller;
    }

}
