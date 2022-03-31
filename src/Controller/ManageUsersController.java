/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Database;
import Model.Display;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class ManageUsersController implements Initializable {

    private Database database = new Database();
    private Display display = new Display();

    @FXML
    private TableView<User> UserTable;

    @FXML
    private TableColumn<User, String> UserId;

    @FXML
    private TableColumn<User, String> Name;

    @FXML
    private TableColumn<User, String> Username;

    @FXML
    private TableColumn<User, String> Active;

    @FXML
    private TableColumn<User, String> Permission;

//    @FXML
//    private TableColumn<User, String> PermissionLevel; 
    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        display.DisplayMenu("Home", event);
    }

    @FXML
    public void handleCreateUser(ActionEvent event) throws IOException {
        User.childUser = null;
        display.DisplayMenu("ModifyUser", event);
    }

    @FXML
    public void handleModifyUser(ActionEvent event) throws IOException {
        User.childUser = UserTable.getSelectionModel().getSelectedItem();
        if (User.childUser != null) {
            display.DisplayMenu("ModifyUser", event);
        } else {
            display.DisplayErrorMessage("missingSelection", "missingMessage");
        }

    }

    @FXML
    public void handleDeleteUser(ActionEvent event) throws IOException {
        User.childUser = UserTable.getSelectionModel().getSelectedItem();
        

        if (User.childUser != null) {
            int childId = User.childUser.getUserId();
            ResourceBundle language = ResourceBundle.getBundle("lang", Locale.getDefault());

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle(language.getString("deleteTitle"));
            alert.setContentText(language.getString("deleteMessage"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(language.getString("confirm"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(language.getString("deny"));

            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        database.deleteRecord("user", String.valueOf(childId));
                        database.buildUserTable(UserTable);
                    });
        } else {
            display.DisplayErrorMessage("missingSelection", "missingMessage");
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        UserId.setCellValueFactory(cellData -> cellData.getValue().getUserIdStringProperty());
        Name.setCellValueFactory(cellData -> cellData.getValue().getName());
        Username.setCellValueFactory(cellData -> cellData.getValue().getUserName());
        Active.setCellValueFactory(cellData -> cellData.getValue().getActiveStatusString());
        Permission.setCellValueFactory(cellData -> cellData.getValue().getPermissionLevelString());
        database.buildUserTable(UserTable);
    }

}
