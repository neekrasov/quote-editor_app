package com.app.controllers.EditQuotes;

import com.app.database.models.Quote;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UpdateQuoteController {
    Quote quoteModel;
    TableView<Quote> dataTable;

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

            quoteModel.setQuote(quote);
            quoteModel.setLecturer(lecturer);
            quoteModel.setSubject(subject);
            quoteModel.setDate(date);

            changeButton.getScene().getWindow().hide();

            quoteModel.update(quote, lecturer, subject, date);
            dataTable.refresh();
        }));
    }

    public void setParentTable(TableView<Quote> dataTable){
        this.dataTable = dataTable;
    }

    public void setQuote(Quote quoteModel){
        this.quoteModel = quoteModel;

        quoteField.setText(quoteModel.getQuote());
        lecturerNameField.setText(quoteModel.getLecturer());
        subjectField.setText(quoteModel.getSubject());
        dateField.setValue(LocalDate.parse(quoteModel.getDate()));
    }

}
