package com.app.controllers;

import com.app.database.models.Quote;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ChangeQuoteController {
    EditMenuController parent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button changeButton;

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
        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
        });

        changeButton.setOnAction((actionEvent -> {

            String quote = quoteField.getText();
            String lecturer = lecturerNameField.getText();
            String subject = subjectField.getText();
            String date = String.valueOf(dateField.getValue());

            Quote.update(quote, lecturer, subject,date);
            exitButton.getScene().getWindow().hide();
            parent.loadTable();
        }));
    }

    public void setParent(EditMenuController controller, Quote currentQuote){
        this.parent = controller;

        quoteField.setText(currentQuote.getQuote());
        subjectField.setText(currentQuote.getSubject());
        dateField.setValue(LocalDate.parse(currentQuote.getDate()));
        lecturerNameField.setText(currentQuote.getLecturer());
    }

}
