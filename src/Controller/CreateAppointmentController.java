/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Model.Display;
import Model.Database;
import java.io.IOException;
import Model.Customer;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import Model.ChecksAndBalances;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import com.storper.matthew.Main;
import Model.DateTime;
import Model.User;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class CreateAppointmentController implements Initializable {

    Display display = new Display();
    Database database = new Database();
    DateTime convertDate = new DateTime();
    Customer customer = new Customer();
    ChecksAndBalances check = new ChecksAndBalances();
    Main main = new Main();

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
    private ComboBox Type;

    @FXML
    private ComboBox StartHour;

    @FXML
    private ComboBox EndHour;

    @FXML
    private ComboBox EndMinutes;

    @FXML
    private ComboBox StartMinutes;

    @FXML
    private ComboBox Location;

    @FXML
    private DatePicker Date;

    @FXML
    private Label AmPm1;

    @FXML
    private Label AmPm2;    

    @FXML
    private TextArea Description;

    @FXML
    private TextField Contact;

    @FXML
    private TextField MeetingTitle;

    @FXML
    private TextField Url;

    @FXML
    void handleBackButtonAction(ActionEvent event) throws IOException {
        display.DisplayMenu("ViewAppointments", event);
    }

    @FXML
    void handleCreateAppointmentAction(ActionEvent event) throws IOException {
        boolean nullBoxCheck = check.nullBoxCheck(Location, Type, StartHour,
                StartMinutes, EndHour, EndMinutes);
        boolean textFieldCheck = check.checkFields(MeetingTitle, Contact);
        boolean dateNotEmpty = check.dateNotEmpty(Date);

        if (nullBoxCheck && textFieldCheck && dateNotEmpty) {

            String appointmentId = Integer.toString(database.generateIds("appointment"));
            String custId = main.getCustId();
            int userId = User.currentUser.getUserId();
            String userName = User.currentUser.getUserName().getValue();
            String title = MeetingTitle.getText();
            String description = "null";
            String location = Location.getValue().toString();
            String contact = Contact.getText();
            String type = Type.getValue().toString();
            String url = "null";
            LocalDate selectedDate = Date.getValue();
            String date;
            String startHour = StartHour.getValue().toString();
            String startMinutes = StartMinutes.getValue().toString();
            String endHour = EndHour.getValue().toString();
            String endMinutes = EndMinutes.getValue().toString();
            int startInt = Integer.parseInt(startHour);
            int endInt = Integer.parseInt(endHour);
            String timeStamp = "CURRENT_TIMESTAMP";

            if (selectedDate != null) {
                date = selectedDate.toString();

                if (startInt < 5) {
                    startInt += 12;
                    startHour = String.valueOf(startInt);
                } else if(startInt > 5 && startInt <=9) {
                    
                    startHour = String.valueOf("0" + startInt);
                }

                if (endInt <= 5) {
                    endInt += 12;
                    endHour = String.valueOf(endInt);
                } else if(endInt > 5 && endInt <=9) {
                    
                    endHour = String.valueOf("0" + endInt);
                }

                if (Description.getText() != null) {
                    description = Description.getText();                   
                    description = description.replaceAll("[/:!<>?']*","");
                }

                if (Url.getText() != null) {
                    url = Url.getText();
                    url = url.replaceAll("[/:!<>?']*","");
                }

                LocalDate newDate = convertDate.stringToDate(date);
                LocalTime startTime = convertDate.stringToTime(startHour + ":" + startMinutes);
                LocalTime endTime = convertDate.stringToTime(endHour + ":" + endMinutes);
                Timestamp startDateTime = convertDate.convertToUTC(newDate, startTime);
                Timestamp endDateTime = convertDate.convertToUTC(newDate, endTime);

                boolean noConflict = database.noAppointmentConflict(startDateTime, endDateTime, appointmentId);
                if (noConflict) {

                    try {
                        database.InsertIntoDatabase("appointment", appointmentId, custId, String.valueOf(userId), title, description,
                                location, contact, type, url, startDateTime.toString(), endDateTime.toString(),
                                timeStamp, userName, timeStamp, userName);
                        display.DisplayMenu("ViewAppointments", event);
                    } catch (SQLException se) {
                        
                        main.updateLog("error", se.toString());
                    }
                }
            }
        }
    }

    @FXML
    void handleHomeButtonAction(ActionEvent event) throws IOException {
        display.DisplayMenu("Home", event);
    }

    @FXML
    void handleAddContactAction(MouseEvent event) throws IOException {
        Customer selectedContact = CustomerTable.getSelectionModel().getSelectedItem();
        if (selectedContact != null) {
            Contact.setText(selectedContact.getCustName().getValue());
            Contact.setStyle("-fx-background-color: LightGray");
            main.setCustId(selectedContact.getCustId().getValue());
        }

    }

    @FXML
    void handleStartHourSelection(ActionEvent event) {
        String selectedHour = StartHour.getValue().toString();

        if (selectedHour != null) {

            check.minuteUpdate(StartHour, StartMinutes, EndHour, EndMinutes);
            check.hourUpdate(StartHour, StartMinutes, EndHour, EndMinutes);
            check.setLabels(StartHour, EndHour, AmPm1, AmPm2);
        }
    }

    @FXML
    void handleStartMinutesSelection(ActionEvent event) {
        check.minuteUpdate(StartHour, StartMinutes, EndHour, EndMinutes);
        check.hourUpdate(StartHour, StartMinutes, EndHour, EndMinutes);
    }

    @FXML
    void handleEndTimeSelection(ActionEvent event) {
        check.minuteUpdate(StartHour, StartMinutes, EndHour, EndMinutes);
        check.setLabels(StartHour, EndHour, AmPm1, AmPm2);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Id.setCellValueFactory(cellData -> cellData.getValue().getCustId());
        Name.setCellValueFactory(cellData -> cellData.getValue().getCustName());
        Address.setCellValueFactory(cellData -> cellData.getValue().getCustAddress());
        Phone.setCellValueFactory(cellData -> cellData.getValue().getCustPhone());
        database.buildCustomerTable(CustomerTable);
        Location.getItems().addAll("ON-PREMISE", "VIRTUAL");
        Type.getItems().addAll("Therapy", "Career Counseling", "Meditation Class", "Exercise Class");
        StartHour.getItems().addAll(9, 10, 11, 12, 1, 2, 3, 4);
        StartMinutes.getItems().addAll("00", "15", "30", "45");
        check.setCalendar(Date);
        check.setLabels(StartHour, EndHour, AmPm1, AmPm2);

    }

}
