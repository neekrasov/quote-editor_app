package com.app.controllers.EditQuotes;

import com.app.animations.Shake;
import com.app.database.models.Quote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

import static com.app.Configs.openNewWindow;

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
    private Button retrieveButton;

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
        retrieveButton.setOnAction(actionEvent -> {
            Quote current = dataTable.getSelectionModel().getSelectedItem();
            FXMLLoader loader = openNewWindow("/com/app/retrieve_quote-view.fxml", retrieveButton, false);
            if(current==null){
                new Shake(retrieveButton).play();
                return;
            }
            RetrieveQuoteController retrieveQuoteController = loader.getController();
            retrieveQuoteController.setQuote(current);
        });
    }

    public void loadTable() {
        loadTable(quote, lecturer, subject, date, dataTable);
    }

    static void loadTable(TableColumn<Quote, String> quote, TableColumn<Quote, String> lecturer, TableColumn<Quote, String> subject, TableColumn<Quote, String> date, TableView<Quote> dataTable) {
        quote.setCellValueFactory(new PropertyValueFactory<>("quote"));
        lecturer.setCellValueFactory(new PropertyValueFactory<>("lecturer"));
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        ObservableList<Quote> data = FXCollections.observableArrayList(Quote.all());
        dataTable.setItems(data);
    }

}
