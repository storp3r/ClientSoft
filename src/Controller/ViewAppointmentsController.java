/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Appointment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import Model.Display;
import Model.Database;
import java.io.IOException;
import java.util.Locale;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Modality;
import com.storper.matthew.Main;
import Model.ChecksAndBalances;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class ViewAppointmentsController implements Initializable {
    private final Main main = new Main();
    Database database = new Database();
    Appointment appointment = new Appointment();
    Display display = new Display();
    ChecksAndBalances cb = new ChecksAndBalances();
    
    @FXML
    private TableView<Appointment> AppointmentTable;    
    @FXML
    private TableColumn<Appointment, String> AppointmentId;
    @FXML
    private TableColumn<Appointment, String> Title;
    @FXML
    private TableColumn<Appointment, String> Description;
    @FXML
    private TableColumn<Appointment, String> Location;
    @FXML
    private TableColumn<Appointment, String> CustomerName;
    @FXML
    private TableColumn<Appointment, String> Type;
    @FXML
    private TableColumn<Appointment, String> StartDateTime;
    @FXML
    private TableColumn<Appointment, String> EndDateTime;
    @FXML
    private TableColumn<Appointment, String> Url;
    
    @FXML
    private ComboBox YearCombo;

    @FXML
    private ComboBox MonthCombo;

    @FXML
    private DatePicker WeekPicker;
    
    @FXML
    private Button resetButton;

    @FXML
    void handleCreateAppointmentButton(ActionEvent event) throws IOException {
        display.DisplayMenu("CreateAppointment", event);
    }

    @FXML
    void handleDeleteButtonAction(ActionEvent event) {
        Appointment currentAppointment  = AppointmentTable.getSelectionModel().getSelectedItem();
        
        if(currentAppointment != null) {
            String appointmentId = currentAppointment.getAppointmentId().getValue();
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
                    database.deleteAppointment("appointment", appointmentId);
                    });
            database.buildAppointmentTable(AppointmentTable);
        } else {
            display.DisplayErrorMessage("missingSelection", "missingMessage");
        }

    }
    
    @FXML
    void handleFilterByMonth(ActionEvent event) {
        String month = MonthCombo.getSelectionModel().getSelectedItem().toString();
        String year = YearCombo.getSelectionModel().getSelectedItem().toString();
        if((month != null) && (year != null)) {
        database.buildAppointmentTable(AppointmentTable, month, year);
        } else {
            System.out.println("month and year are not selected");
        }
        
    }
    
    @FXML
    void handleFilterByWeek(ActionEvent event) {
        int selectedYear = WeekPicker.getValue().getYear();
        int selectedMonth = WeekPicker.getValue().getMonthValue();
        int selectedDay = WeekPicker.getValue().getDayOfMonth();
        if(WeekPicker.getValue() != null) {
        database.buildAppointmentTable(AppointmentTable, selectedYear, selectedMonth, selectedDay);
        }      

    }


    @FXML
    void handleHomeButtonAction(ActionEvent event) throws IOException {
        display.DisplayMenu("Home", event);
    }

    @FXML
    void handleModifyButtonAction(ActionEvent event) throws IOException {
        Appointment currentAppointment = AppointmentTable.getSelectionModel().getSelectedItem();
        
        if(currentAppointment != null) {
        String appointmentId = currentAppointment.getAppointmentId().getValue();
        main.setAppointmentId(appointmentId);
        
        display.DisplayMenu("ModifyAppointment", event);
        } else {
            display.DisplayErrorMessage("missingSelection", "missingMessage");
        }

    }
    
    @FXML
    void handleResetAction(ActionEvent event) {
    database.buildAppointmentTable(AppointmentTable);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        AppointmentId.setCellValueFactory(cellData -> cellData.getValue().getAppointmentId());
        Title.setCellValueFactory(cellData -> cellData.getValue().getTitle());
        Description.setCellValueFactory(cellData -> cellData.getValue().getDescription());
        Location.setCellValueFactory(cellData -> cellData.getValue().getLocation());
        CustomerName.setCellValueFactory(cellData -> cellData.getValue().getContact());
        Type.setCellValueFactory(cellData -> cellData.getValue().getMeetingType());
        StartDateTime.setCellValueFactory(cellData -> cellData.getValue().getStart());
        EndDateTime.setCellValueFactory(cellData -> cellData.getValue().getEnd());
        Url.setCellValueFactory(cellData -> cellData.getValue().getUrl());
        database.buildAppointmentTable(AppointmentTable);
        
        MonthCombo.getItems().addAll("January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December");

        YearCombo.getItems().addAll("2021","2022","2023","2024","2025");
        cb.setCalendar(WeekPicker, "week");
        
    }    
    
}
