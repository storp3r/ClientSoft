/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import Model.Display;
import java.util.Locale;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.storper.matthew.Main;
import Model.Database;
import Model.User;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class HomeController implements Initializable {

    private ResourceBundle language = ResourceBundle.getBundle("lang", Locale.getDefault());
    Display display = new Display();
//    Main main = new Main();
    Database database = new Database();
    
    @FXML
    private ImageView menuGraphic;
    @FXML
    private MenuButton account;    
    @FXML
    private TableView<Appointment> UpcomingTable;
    @FXML
    private TableColumn<Appointment, String> CustomerName;
    @FXML
    private TableColumn<Appointment, String> Location;
    @FXML
    private TableColumn<Appointment, String> Type;
    @FXML
    private TableColumn<Appointment, String> StartDateTime;

    @FXML
    private Label WelcomeLabel;

   
    @FXML
    void handleAppointmentsButton(ActionEvent event) throws IOException {
        display.DisplayMenu("ViewAppointments", event);
    }

    @FXML
    void handleViewCustomersDisplay(ActionEvent event) throws IOException {
        display.DisplayMenu("ViewCustomers", event);
    }
    
    @FXML
    void handleViewReportsButton(ActionEvent event) throws IOException {
        display.DisplayMenu("ViewReports", event);
    }

    @FXML
    public void handleLogOutButtonAction(ActionEvent event) throws IOException {
        display.DisplayMenu("Login", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Database.getConnection();
        WelcomeLabel.setText(language.getString("welcomeText") + ", " + User.currentUser.getUserName().getValue() + "!");        
        

        MenuItem changePassword = new MenuItem("Change Password");
        MenuItem manageUsers = new MenuItem("Manage Users");        
        MenuItem logout = new MenuItem("Logout");  
        
        changePassword.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    display.DisplayMenu("ChangePassword", account);
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        manageUsers.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                try {
                    display.DisplayMenu("ManageUsers", account);
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
        logout.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                              try {
                    display.DisplayMenu("Login", account);
                } catch (IOException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }                
            }
        });
        
        if(User.currentUser.getPermissionLevel() > 1){
            account.getItems().addAll(changePassword, manageUsers,logout);
        }else{
            account.getItems().addAll(changePassword, logout);
        }
        
        
        UpcomingTable.setPlaceholder(new Label("No Upcoming Appointments"));
        CustomerName.setCellValueFactory(cellData -> cellData.getValue().getContact());
        Location.setCellValueFactory(cellData -> cellData.getValue().getLocation());
        Type.setCellValueFactory(cellData -> cellData.getValue().getMeetingType());
        StartDateTime.setCellValueFactory(cellData -> cellData.getValue().getStart());
        database.buildUpcomingTable(UpcomingTable);
        


    }

}
