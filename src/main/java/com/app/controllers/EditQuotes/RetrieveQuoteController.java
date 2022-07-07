package com.app.controllers.EditQuotes;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.app.database.models.Quote;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RetrieveQuoteController {

    Quote quoteModel;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    }

    public void setQuote(Quote quoteModel){
        this.quoteModel = quoteModel;

        quoteField.setText(quoteModel.getQuote());
        lecturerNameField.setText(quoteModel.getLecturer());
        subjectField.setText(quoteModel.getSubject());
        dateField.setValue(LocalDate.parse(quoteModel.getDate()));
    }

}
