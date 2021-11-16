/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Model.Display;
import Model.Database;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import com.storper.matthew.Main;
import Model.ChecksAndBalances;
import Model.User;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class CreateCustomerController implements Initializable {
    private final Main main = new Main();
    Database database = new Database();
    Display display = new Display();
    ChecksAndBalances check = new ChecksAndBalances();

    @FXML
    private Label windowText;

    @FXML
    private Label firstNmLabel;

    @FXML
    private Label lastNmLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label addressTwoLabel;

    @FXML
    private Label cityLabel;

    @FXML
    private Label countryLabel;

    @FXML
    private Label postalLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField firstNmField;

    @FXML
    private TextField lastNmField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField addressTwoField;

    @FXML
    private TextField cityField;

    @FXML
    private TextField countryField;

    @FXML
    private TextField postalField;

    @FXML
    private TextField phoneField;
    
    
    private String customerName;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    void handleAddCustomerAction(ActionEvent event) throws SQLException, IOException{
        boolean cleared = check.checkFields(firstNmField, lastNmField, addressField, cityField, countryField, postalField, phoneField);
            System.out.println(postalField.getText() + " " + phoneField.getText());
        boolean clearedIntegers = check.checkIntegers(postalField, phoneField);       
        String username = User.currentUser.getUserName().getValue();
        System.out.println(username);
        if ((cleared) && (clearedIntegers)) {
            String id = Integer.toString(database.generateIds("customer"));
            customerName = firstNmField.getText() + " " + lastNmField.getText();
            
            
            database.InsertIntoDatabase("country", id, countryField.getText(), "CURRENT_TIMESTAMP", username, "CURRENT_TIMESTAMP", username);
            database.InsertIntoDatabase("city", id, cityField.getText(), id, "CURRENT_TIMESTAMP", username, "CURRENT_TIMESTAMP",
                    username);
            database.InsertIntoDatabase("address", id, addressField.getText(), addressTwoField.getText(), id, postalField.getText(),
                    phoneField.getText(), "CURRENT_TIMESTAMP", username, "CURRENT_TIMESTAMP", username);
            database.InsertIntoDatabase("customer", id, customerName, id, "1", "CURRENT_TIMESTAMP", username, "CURRENT_TIMESTAMP", username);           
            display.DisplayMenu("ViewCustomers", event);
        } else {
        
            System.out.println("Try again");
        }
        
        
    }
    
    @FXML
    public void handleHomeButtonAction(ActionEvent event) throws IOException {
        display.DisplayMenu("ViewCustomers", event);
    }

}
