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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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
     display.DisplayMenu("ModifyUser", event);
         System.out.println(User.childUser.getName().getValue());
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
