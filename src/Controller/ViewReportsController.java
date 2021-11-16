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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import Model.Appointment;
import Model.Database;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleGroup;
import Model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.storper.matthew.Main;
import Model.ChecksAndBalances;
import Model.Display;
import javafx.scene.control.TableColumn;
import Model.NewClient;
import java.io.IOException;
import Model.AppointmentType;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class ViewReportsController implements Initializable {

    Database database = new Database();
    ObservableList<User> userArray = FXCollections.observableArrayList();
    Main main = new Main();
    Display display = new Display();
    ChecksAndBalances check = new ChecksAndBalances();
    String tempName;
    String tempId;

    @FXML
    private TableView<Appointment> ScheduleTable;
    @FXML
    private TableColumn<Appointment, String> ScheduleStart;
    @FXML
    private TableColumn<Appointment, String> ScheduleLocation;
    @FXML
    private TableColumn<Appointment, String> ScheduleContact;
    @FXML
    private TableColumn<Appointment, String> ScheduleType;

    @FXML
    private TableView<NewClient> ClientTable;
    @FXML
    private TableColumn<NewClient, String> ClientYear;
    @FXML
    private TableColumn<NewClient, String> ClientMonth;
    @FXML
    private TableColumn<NewClient, String> ClientCount;

    @FXML
    private TableView<Appointment> AppointmentTypeTable;
    @FXML
    private TableColumn<AppointmentType, String> AppYear;
    @FXML
    private TableColumn<AppointmentType, String> AppMonth;
    @FXML
    private TableColumn<AppointmentType, String> AppCount;    

    @FXML
    private ComboBox<User> ScheduleCombo;

    @FXML
    private ToggleGroup ReportChoice;

    @FXML
    private ComboBox<User> ClientCombo;

    @FXML
    private RadioButton ScheduleRadio;

    @FXML
    private RadioButton NewClientRadio;
    

    @FXML
    void handleScheduleConsulatantSelection(ActionEvent event) {
        setTempIdAndName(ScheduleCombo);

    }

    @FXML
    void handleClientConsultantSelection(ActionEvent event) {
        setTempIdAndName(ClientCombo);
    }

    @FXML
    void handleGenerateReportButton(ActionEvent event) {
        Object choice = ReportChoice.getSelectedToggle();
        boolean cleared = false;
        
        if (choice == ScheduleRadio) {
            cleared = check.nullBoxCheck(ScheduleCombo);
            if (cleared) {
                ScheduleTable.setVisible(true);
                ClientTable.setVisible(false);
                AppointmentTypeTable.setVisible(false);
                System.out.println("cleared should be building");
                database.buildAppointmentTable(ScheduleTable, Integer.parseInt(tempId));
            }

        } else if (choice == NewClientRadio) {
            cleared = check.nullBoxCheck(ClientCombo);
            if (cleared) {
                ScheduleTable.setVisible(false);
                ClientTable.setVisible(true);
                AppointmentTypeTable.setVisible(false);
               
                database.buildNewClientTable(ClientTable, tempName, tempId);
            }

        } else {
            cleared = true;
                ScheduleTable.setVisible(false);
                ClientTable.setVisible(false);
                AppointmentTypeTable.setVisible(true);
                database.buildAppointmentTypeTable(AppointmentTypeTable);
        }

        if (cleared == false) {
            System.out.println("null detected");
        }
    }
    
    @FXML
    void handleHomeButtonAction(ActionEvent event) throws IOException {
        display.DisplayMenu("Home", event);
    }

    public void loadUsers(ComboBox box1, ComboBox box2) {
        ResultSet rs = database.getUsers();

        try {

            while (rs.next()) {
                User user = new User();
                user.setUserDetails(
                        rs.getInt("userId"),
                        rs.getString("userName")
                );
                userArray.add(user);
            }

            int i = 0;
            while (i < userArray.size()) {
                User user = userArray.get(i);
                String username = user.getUserName().getValue();
                box1.getItems().add(username);
                box2.getItems().add(username);
                i++;
            }

        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
    }

    public void setTempIdAndName(ComboBox comboBox) {
        int index = comboBox.getSelectionModel().getSelectedIndex();
        User currentUser = userArray.get(index);
        String userId = String.valueOf(currentUser.getUserId());
        String username = currentUser.getUserName().getValue();
        
        tempName = username;
        tempId = userId;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        loadUsers(ScheduleCombo, ClientCombo);
        ScheduleStart.setCellValueFactory(cellData -> cellData.getValue().getStart());
        ScheduleLocation.setCellValueFactory(cellData -> cellData.getValue().getLocation());
        ScheduleContact.setCellValueFactory(cellData -> cellData.getValue().getContact());
        ScheduleType.setCellValueFactory(cellData -> cellData.getValue().getMeetingType());
        
        ClientYear.setCellValueFactory(cellData -> cellData.getValue().getYear());
        ClientMonth.setCellValueFactory(cellData -> cellData.getValue().getMonth());
        ClientCount.setCellValueFactory(cellData -> cellData.getValue().getCount());
        
        AppYear.setCellValueFactory(cellData -> cellData.getValue().getYear());
        AppMonth.setCellValueFactory(cellData -> cellData.getValue().getMonth());
        AppCount.setCellValueFactory(cellData -> cellData.getValue().getCount());
    }

}
