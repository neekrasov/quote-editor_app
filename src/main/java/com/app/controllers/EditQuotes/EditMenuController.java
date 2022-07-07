package com.app.controllers.EditQuotes;

import com.app.animations.Shake;
import com.app.controllers.Profile.UserProfileController;
import com.app.database.models.Quote;
import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.app.Configs.openNewWindow;

public class EditMenuController {
    User user;

    ArrayList<Integer> userFunctions;
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
    private Button retrieveButton;

    @FXML
    void initialize() {
        loadTable();

        dataTable.setRowFactory(tv -> new TableRow<Quote>() {
            @Override
            public void updateItem(Quote item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                } else if (user == null) {
                } else if (!(item.getAuthor() == user.getId())) {
                    setStyle("-fx-background-color: #C0C0C0;");
                }
            }
        });

        profileButton.setOnAction(actionEvent -> {
            FXMLLoader loader = openNewWindow("/com/app/user_profile-view.fxml", exitButton, true);
            UserProfileController userProfileController = loader.getController();
            try {
                userProfileController.setUser(user);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        exitButton.setOnAction(actionEvent -> {
            openNewWindow("/com/app/authorization-view.fxml", exitButton, true);
        });

        addButton.setOnAction(actionEvent -> {
            try {
                if (accessCheck(null, addButton, 1)) return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            FXMLLoader loader = openNewWindow("/com/app/add_quote-view.fxml", addButton, false);
            CreateQuoteController createQuoteController = loader.getController();
            createQuoteController.setParentTable(dataTable);
            createQuoteController.setUser(user);
        });

        changeButton.setOnAction(actionEvent -> {
            Quote current = getCurrentQuote();
            try {
                if (accessCheck(current, changeButton, 5)) return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            FXMLLoader loader = openNewWindow("/com/app/change_quote-view.fxml", changeButton, false);
            UpdateQuoteController updateQuoteController = loader.getController();
            updateQuoteController.setParentTable(dataTable);
            updateQuoteController.setQuote(current);
        });

        deleteButton.setOnAction(actionEvent -> {
            Quote current = getCurrentQuote();
            try {
                if (accessCheck(current, deleteButton, 6)) return;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            dataTable.getItems().removeAll(current);
            current.delete();
            dataTable.refresh();
        });

        retrieveButton.setOnAction(actionEvent -> {
            Quote current = getCurrentQuote();
            FXMLLoader loader = openNewWindow("/com/app/retrieve_quote-view.fxml", retrieveButton, false);
            if (current == null) {
                new Shake(retrieveButton).play();
                return;
            }
            RetrieveQuoteController retrieveQuoteController = loader.getController();
            retrieveQuoteController.setQuote(current);
        });
    }

    public void loadTable() {
        GuestMenuController.loadTable(quote, lecturer, subject, date, dataTable);
    }

    public Quote getCurrentQuote() {
        return dataTable.getSelectionModel().getSelectedItem();
    }

    public void setUser(User user) throws SQLException {
        this.user = user;
        this.userFunctions = user.getFunctionsFromDB();
    }

    public boolean accessCheck(Quote quote, Button button, int code) throws SQLException {
        if (quote == null && code != 1) return accessFailed(button);

        if (user.isVerifier()){
            return verifierAccessCheck(quote, button, code);
        }

        if (userFunctions == null) return accessFailed(button);

        if (user.isStaff()) return false;


        if (code == 1 && (!userFunctions.contains(code))) return accessFailed(button);

        // Приоритет отдаётся более ограниченной функции.
        // Верифицированный пользователь по-умолчанию может изменять свои и чужие записи.

        if (code == 6) {
            if (userFunctions.contains(6) && quote.getAuthor() != user.getId()) return accessFailed(button);
            else if (!userFunctions.contains(6)) return !userFunctions.contains(3) && accessFailed(button);
        }
        if (code == 5)
            if (userFunctions.contains(5) && quote.getAuthor() != user.getId()) return accessFailed(button);
            else if (!userFunctions.contains(5)) return !userFunctions.contains(2) && accessFailed(button);

        return false;
    }

    private boolean accessFailed(Button button) {
        new Shake(button).play();
        return true;
    }

    public void refreshTable() {
        dataTable.refresh();
    }

    private boolean verifierAccessCheck(Quote quote, Button button, int code) {
        List<Integer> list = user.getGroup().stream().map(User::getId).toList();
        if (code == 1){
            return false;
        }else if (quote.getAuthor() == user.getId()) {
            return false;
        } else if (list.contains(quote.getAuthor())) {
            return false;
        }
        return accessFailed(button);
    }
}

