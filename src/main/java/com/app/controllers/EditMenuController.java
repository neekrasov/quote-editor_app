package com.app.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.app.animations.Shake;
import com.app.database.models.Quote;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import static com.app.controllers.AuthController.openNewWindow;

public class EditMenuController {
    AddQuoteController addQuoteController;
    ChangeQuoteController changeQuoteController;

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
        loadTable();

        exitButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/authorization-view.fxml", exitButton, true);
        });

        addButton.setOnAction(actionEvent -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/add_quote-view.fxml"));
            Stage stage = new Stage();

            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();

            addQuoteController = loader.getController();
            addQuoteController.setParent(this);
        });

        changeButton.setOnAction(actionEvent -> {
            Quote current = getCurrentQuote();
            if (current == null) {
                Shake button = new Shake(changeButton);
                button.play();
                return;
            }
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/app/change_quote-view.fxml"));
            Stage stage = new Stage();

            try {
                stage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.show();

            changeQuoteController = loader.getController();
            changeQuoteController.setParent(this, current);
        });

        deleteButton.setOnAction(event -> {
            Quote current = getCurrentQuote();
            if (current == null) {
                Shake button = new Shake(deleteButton);
                button.play();
                return;
            }
            dataTable.getItems().removeAll(current);
            current.delete();
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

    public Quote getCurrentQuote() {
        ArrayList<Quote> items = new ArrayList<>(dataTable.getSelectionModel().getSelectedItems());
        if (items.size() == 0) {
            return null;
        }
        return items.get(0);
    }

}

