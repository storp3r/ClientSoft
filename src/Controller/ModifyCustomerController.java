/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Address;
import Model.Customer;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import Model.Database;
import Model.Display;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.storper.matthew.Main;
import javafx.event.ActionEvent;
import Model.ChecksAndBalances;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class ModifyCustomerController implements Initializable {

    private final Main main = new Main();
    Database database = new Database();
    Display display = new Display();
    ChecksAndBalances check = new ChecksAndBalances();
    Customer currentCustomer = new Customer();
    Address currentAddress = new Address();
  
    
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

    @FXML
    public void handleUpdateCustomerButton(ActionEvent event) throws IOException {
        boolean cleared = check.checkFields(firstNmField, lastNmField, addressField, cityField, countryField, postalField, phoneField);
        boolean clearedIntegers = check.checkIntegers(postalField, phoneField);
        String customerName = firstNmField.getText() + " " + lastNmField.getText();

        if ((cleared) && (clearedIntegers)) {
            database.updateCustomer(customerName, main.getCustId());

            if (addressTwoField.getText().isEmpty()) {
                database.updateAddress(
                        addressField.getText(),
                        postalField.getText(),
                        phoneField.getText(),
                        main.getCustId());
            } else {
                database.updateAddress(
                addressField.getText(),
                        addressTwoField.getText(),
                        postalField.getText(),
                        phoneField.getText(),
                        main.getCustId()
                );
            
            }
            
            database.updateCity(cityField.getText(), main.getCustId());
            database.updateCountry(countryField.getText(), main.getCustId());
            
            display.DisplayMenu("ViewCustomers", event);
            
        }
        
        
    }
    
    @FXML
    public void handleBackButtonAction(ActionEvent event) throws IOException {
    display.DisplayMenu("ViewCustomers", event);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        loadCustomer();

    }

    public void loadCustomer() {
        
        ResultSet rs = database.retrieveRecordById(main.getCustId());

        try {
            if (rs.next()) {
                String customerName = rs.getString("customerName");
                String firstName = customerName.split(" ")[0];
                String lastName = customerName.split(" ")[1];
                String address = rs.getString("address");
                String address2 = rs.getString("address2");
                String city = rs.getString("city");
                String country = rs.getString("country");
                String postal = rs.getString("postalCode");
                String phone = rs.getString("phone");

                firstNmField.setText(firstName);
                lastNmField.setText(lastName);
                addressField.setText(address);
                addressTwoField.setText(address2);
                cityField.setText(city);
                countryField.setText(country);
                postalField.setText(postal);
                phoneField.setText(phone);
            }
        } catch (SQLException se) {           
            main.updateLog("error", se.toString());
        }

    }

}
