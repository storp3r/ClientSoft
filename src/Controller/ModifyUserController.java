/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ChecksAndBalances;
import Model.CryptWithMD5;
import Model.Database;
import Model.Display;
import Model.User;
import com.storper.matthew.Main;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class ModifyUserController extends User implements Initializable {

    Display display = new Display();
    ChecksAndBalances verify = new ChecksAndBalances();
    Database database = new Database();
    Main main = new Main();
    User createdUser = new User();
    User child = User.childUser;
    private String currentPassword;

    @FXML
    private Label windowText;

//    @FXML
//    private Button addCustomerButton;
    @FXML
    private TextField nameField;

    @FXML
    private TextField userNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox activeComboBox;

    @FXML
    private ComboBox permissionComboBox;

    @FXML
    void handleSaveUserAction(ActionEvent event) {

        if (verify.checkFields(nameField, userNameField, passwordField, confirmPasswordField)) {
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();
            String name = nameField.getText().trim();
            String userName = userNameField.getText().trim();
            String currentUser = User.currentUser.getUserName().getValue();
            int permissionLevel = createdUser.convertStringToPermission(permissionComboBox.getValue().toString());
            int activeLevel = createdUser.convertStringToActive(activeComboBox.getValue().toString());

            if (password.equals(confirmPassword)) {
                //TODO SAVE USER
                if(child == null) {
                    database.insertUser(name, userName, confirmPassword, activeLevel, permissionLevel, currentUser);
                } else {
                    if(!currentPassword.equals(confirmPassword)){
                        confirmPassword = CryptWithMD5.cryptWithMD5(confirmPassword);
                    } 
                    database.updateUser(child.getUserId(), name, userName, confirmPassword, activeLevel, permissionLevel, currentUser);
                }
                
            } else {
                display.DisplayErrorMessage("passwordMatchError", "passwordMatchMessage");
            }

        }

    }

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        display.DisplayMenu("ManageUsers", event);
    }

    public void generateFieldData() {

        

        if (child != null) {
            nameField.setText(child.getName().getValue());
            userNameField.setText(child.getUserName().getValue());
            activeComboBox.getSelectionModel().select(child.getActive());
            permissionComboBox.getSelectionModel().select(child.getPermissionLevel() - 1);
            currentPassword = child.getPassword();
            passwordField.setText(currentPassword);
            confirmPasswordField.setText(currentPassword);
            System.out.println(child.getPassword());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        activeComboBox.getItems().addAll("NO", "YES");
        activeComboBox.getSelectionModel().selectLast();
        permissionComboBox.getItems().addAll(User.currentUser.getPermissionList());
        permissionComboBox.getSelectionModel().selectFirst();
        
        generateFieldData();

    }

}
