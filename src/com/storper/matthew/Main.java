/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.storper.matthew;


import java.io.IOException;
import javafx.application.Platform;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import Model.Database;
import Model.Display;
import Model.User;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import static java.util.Locale.setDefault;
import java.util.ResourceBundle;



/**
 *
 * @author Matt
 */
public class Main extends Application {
    ResourceBundle language = ResourceBundle.getBundle("lang", Locale.getDefault());
    public AnchorPane mainMenu;
    public FXMLLoader fxmlloader;
    private Stage activeWindow;
    public static User currentUser;
    private static String userName;
    private static String userId;
    private static int userPermissions;
    private static String childUserId;
    private static String customerId;
    private static String appointmentId;

    @Override //Uncomment code below to change region
    public void start(Stage stage) throws Exception {
//        setDefault(new Locale("es", "ES"));
        
        activeWindow = stage;
        activeWindow.setTitle("ClientSoft");
        fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(Main.class.getResource("/View/Login.fxml")); 
        mainMenu = (AnchorPane) fxmlloader.load();
        Scene scene = new Scene(mainMenu);
        activeWindow.setScene(scene);
        activeWindow.setResizable(false);

        activeWindow.show();

//        establishConnection();
        Database.setConnection();
       

        try {
            File log = new File("log.txt");
            if (log.createNewFile()) {
                System.out.println("Successfully created file: " + log.getName());
            } else {
                System.out.println("The file: " + log.getName() + " already exists");
            }
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

    }
 

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        launch(args);      

    }
    

    public void updateLog(String info, String type) {
        

        try (FileWriter writer = new FileWriter("log.txt", true);
               BufferedWriter buffered = new BufferedWriter(writer); 
                PrintWriter textToLog = new PrintWriter(buffered)) {
            
           String identifier;
           SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
           Date d1 = new Date(System.currentTimeMillis());  
            
           switch(type.toLowerCase()) {
               case "alert": identifier = "ALERT: ";
               break;
               case "error": identifier = "ERROR: ";
               break;
               case "info": identifier = "INFO: ";
               break;
               default: identifier = "UNDEFINED: ";
               break;
           }      
           
            textToLog.println(sdf.format(d1));
            textToLog.println(identifier + info);
            textToLog.println();  
            
        } catch (SecurityException | IOException sie) {
            System.out.println("error with log");
        }
    }
    
    public void User(){
    
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }
    
    public void setUserPermissions(int permissions){
        this.userPermissions = permissions;
    }
    
    public int getUserPermissions(){
        return this.userPermissions;
    }
    
    public void setChildUserId(String userId) {
        this.childUserId = userId;
    }
    
    public String getChildUserId() {
        return this.childUserId;
    }
    

    public void setCustId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustId() {
        return this.customerId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getAppointmentId() {
        return this.appointmentId;
    }

}
