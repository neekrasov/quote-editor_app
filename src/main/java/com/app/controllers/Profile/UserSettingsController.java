package com.app.controllers.Profile;

import com.app.database.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserSettingsController {

    User selectedUser;

//    ArrayList<Integer> oldFunctions;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button changeButton;

    @FXML
    private Button exitButton;

    @FXML
    private CheckBox fifthCheckBox;

    @FXML
    private CheckBox firstCheckBox;

    @FXML
    private CheckBox fourthCheckBox;

    @FXML
    private CheckBox secondCheckBox;

    @FXML
    private CheckBox thirdCheckBox;

    @FXML
    private Label isAdminLabel;

    @FXML
    private CheckBox staffCheckBox;
    @FXML
    private CheckBox verifierCheckBox;


    @FXML
    void initialize() {

        exitButton.setOnAction(actionEvent -> {
            exitButton.getScene().getWindow().hide();
        });

        changeButton.setOnAction(actionEvent -> {
            int firstCheckBoxValue = firstCheckBox.isSelected() ? 1 : 0;
            int secondCheckBoxValue = secondCheckBox.isSelected() ? 1 : 0;
            int thirdCheckBoxValue = thirdCheckBox.isSelected() ? 1 : 0;
            int fourthCheckBoxValue = fourthCheckBox.isSelected() ? 1 : 0;
            int fifthCheckBoxValue = fifthCheckBox.isSelected() ? 1 : 0;
            boolean staffCheckBoxValue = staffCheckBox.isSelected();
            boolean verifierCheckBoxValue = verifierCheckBox.isSelected();

            ArrayList<Integer> newFunctions = new ArrayList<>();
            ArrayList<Integer> userFunctions = null;
            try {
                userFunctions = selectedUser.getFunctionsFromDB();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            System.out.println("selected user functions: " + userFunctions );

            if (userFunctions == null){
                if (firstCheckBoxValue == 1) newFunctions.add(1);
                if (secondCheckBoxValue == 1) newFunctions.add(2);
                if (thirdCheckBoxValue == 1) newFunctions.add(3);
                if (fourthCheckBoxValue == 1) newFunctions.add(5);
                if (fifthCheckBoxValue == 1) newFunctions.add(6);
            }else{
                if (userFunctions.contains(1)){
                    if (firstCheckBoxValue == 0){
                        selectedUser.deleteFunction(1);
                    }
                }else {
                    if (fifthCheckBoxValue == 1){
                        newFunctions.add(1);
                    }
                }
                if (userFunctions.contains(2)){
                    if (secondCheckBoxValue == 0){
                        selectedUser.deleteFunction(2);
                    }
                }else {
                    if (secondCheckBoxValue == 1){
                        newFunctions.add(2);
                    }
                }
                if (userFunctions.contains(3)){
                    if (thirdCheckBoxValue == 0){
                        selectedUser.deleteFunction(3);
                    }
                }else {
                    if (thirdCheckBoxValue == 1){
                        newFunctions.add(3);
                    }
                }
                if (userFunctions.contains(5)){
                    if (fourthCheckBoxValue == 0){
                        selectedUser.deleteFunction(5);
                    }
                }else {
                    if (fourthCheckBoxValue == 1){
                        newFunctions.add(5);
                    }
                }
                if (userFunctions.contains(6)){
                    if (fifthCheckBoxValue == 0){
                        selectedUser.deleteFunction(6);
                    }
                }else {
                    if (fifthCheckBoxValue == 1){
                        newFunctions.add(6);
                    }
                }
            }

            if (selectedUser.isStaff() != staffCheckBoxValue) {
                selectedUser.setStaff(staffCheckBoxValue);
            }
            if (selectedUser.isVerifier() != verifierCheckBoxValue) {
                selectedUser.setVerifier(verifierCheckBoxValue);
            }

            selectedUser.setFunctions(newFunctions);

            changeButton.getScene().getWindow().hide();
        });
    }

    public void setSelectedUser(User user) throws SQLException {
        this.selectedUser = user;
        if (selectedUser.isStaff()) {
            isAdminLabel.setText("This user is staff");
            staffCheckBox.setSelected(true);
        } else {
            isAdminLabel.setVisible(false);
        }
        if (selectedUser.isVerifier()){
            verifierCheckBox.setSelected(true);
        }
        ArrayList<Integer> functions = selectedUser.getFunctionsFromDB();
        if (functions != null) {
            if (functions.contains(1)) {
                firstCheckBox.setSelected(true);
            }
            if (functions.contains(2)) {
                secondCheckBox.setSelected(true);
            }
            if (functions.contains(3)) {
                thirdCheckBox.setSelected(true);
            }
            if (functions.contains(5)) {
                fourthCheckBox.setSelected(true);
            }
            if (functions.contains(6)) {
                fifthCheckBox.setSelected(true);
            }
        }

    }
}
