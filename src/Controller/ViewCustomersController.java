/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Model.Customer;
import Model.Database;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import Model.Display;
import java.io.IOException;
import java.util.Locale;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import com.storper.matthew.Main;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class ViewCustomersController implements Initializable {

    private final Main main = new Main();
    Database database = new Database();
    Customer customer = new Customer();
    Display display = new Display();

    @FXML
    private TextField searchTextField;
    @FXML
    private Button resetButton;
    @FXML
    private TableView<Customer> CustomerTable;
    @FXML
    private TableColumn<Customer, String> Id;
    @FXML
    private TableColumn<Customer, String> Name;
    @FXML
    private TableColumn<Customer, String> Address;
    @FXML
    private TableColumn<Customer, String> Phone;

    @FXML
    public void handleSearchButton(ActionEvent envent) throws IOException {
        String searchTerm = searchTextField.getText().trim();
        if (!searchTerm.equals("")) {
            if (!database.buildCustomerTable(CustomerTable, searchTerm)) {
                display.DisplayMessage("noResults", "noResultsMessage");
            } else {
                resetButton.setVisible(true);
            }
        } else {
            database.buildCustomerTable(CustomerTable);
            resetButton.setVisible(false);
        }

    }

    @FXML
    public void handleResetTable(ActionEvent event) throws IOException {
        database.buildCustomerTable(CustomerTable);
        resetButton.setVisible(false);
        searchTextField.setText("");
    }

    @FXML
    public void handleAddCustomerButton(ActionEvent event) throws IOException {
        display.DisplayMenu("CreateCustomer", event);
    }

    @FXML
    public void handleDeleteCustomer(ActionEvent event) {
        Customer currentCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        if (currentCustomer != null) {
            String customerId = currentCustomer.getCustId().getValue();
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
                        database.deleteRecord("appointment", customerId);
                        database.deleteRecord("customer", customerId);
                        database.deleteRecord("address", customerId);
                        database.deleteRecord("city", customerId);
                        database.deleteRecord("country", customerId);
                    });
            database.buildCustomerTable(CustomerTable);
        } else {
            display.DisplayErrorMessage("missingSelection", "missingMessage");
        }

    }

    @FXML
    public void handleHomeButtonAction(ActionEvent event) throws IOException {
        display.DisplayMenu("Home", event);
    }

    @FXML
    public void handleModifyButtonAction(ActionEvent event) throws IOException {
        Customer currentCustomer = CustomerTable.getSelectionModel().getSelectedItem();

        if (currentCustomer != null) {
            String customerId = currentCustomer.getCustId().getValue();
            System.out.println(customerId);
            main.setCustId(customerId);

            display.DisplayMenu("ModifyCustomer", event);
        } else {
            display.DisplayErrorMessage("missingSelection", "missingMessage");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {        
        //This lambda used for simple table population
        Id.setCellValueFactory(cellData -> cellData.getValue().getCustId());
        Name.setCellValueFactory(cellData -> cellData.getValue().getCustName());
        Address.setCellValueFactory(cellData -> cellData.getValue().getCustAddress());
        Phone.setCellValueFactory(cellData -> cellData.getValue().getCustPhone());
        database.buildCustomerTable(CustomerTable);
    }

}
