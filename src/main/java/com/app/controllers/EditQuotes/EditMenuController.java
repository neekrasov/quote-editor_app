package com.app.controllers.EditQuotes;

import com.app.animations.Shake;
import com.app.controllers.Profile.UserProfileController;
import com.app.database.models.Quote;
import com.app.database.models.User;
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

import static com.app.controllers.Auth.AuthController.openNewWindow;

public class EditMenuController {
    AddQuoteController addQuoteController;
    ChangeQuoteController changeQuoteController;

    UserProfileController userProfileController;

    User user;

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
    private Button profileButton;

    @FXML
    void initialize() {
        loadTable();

        profileButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/user_profile-view.fxml", exitButton, true);
            userProfileController = loader.getController();
            userProfileController.setUser(user);

        });

        exitButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/authorization-view.fxml", exitButton, true);
        });

        addButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/change_quote-view.fxml", addButton, false);
            addQuoteController = loader.getController();
            addQuoteController.setParentTable(dataTable);
        });

        changeButton.setOnAction(actionEvent -> {
            Quote current = getCurrentQuote();

            if (current == null) {
                Shake button = new Shake(changeButton);
                button.play();
                return;
            }
            FXMLLoader loader = openNewWindow("/com/app/change_quote-view.fxml", changeButton, false);
            changeQuoteController = loader.getController();
            changeQuoteController.setParentTable(dataTable);
            changeQuoteController.setQuote(current);
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
        return dataTable.getSelectionModel().getSelectedItem();
    }

    public void setUser(User user) {
        this.user = user;
    }
}

