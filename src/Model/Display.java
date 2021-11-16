/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import com.storper.matthew.Main;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;


/**
 *
 * @author Matt
 */
public class Display {
    
    Main main = new Main();

    private final ResourceBundle language = ResourceBundle.getBundle("lang", Locale.getDefault());
    
    

    public static Alert DisplayErrorMessage(String windowTitle, String errorMessage) {
        ResourceBundle language = ResourceBundle.getBundle("lang", Locale.getDefault());

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(language.getString(windowTitle));
        alert.setContentText(language.getString(errorMessage));
        alert.showAndWait();
        return alert;
    }
    
    public static Alert DisplayMessage(String windowTitle, String errorMessage) {
        ResourceBundle language = ResourceBundle.getBundle("lang", Locale.getDefault());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setTitle(language.getString(windowTitle));
        alert.setHeaderText(null);
        alert.setContentText(language.getString(errorMessage));
        alert.showAndWait();
        return alert;
    }

    public void DisplayMenu(String menuName, ActionEvent event) throws IOException {
        Parent activeWindow = FXMLLoader.load(getClass().getResource("/View/" + menuName + ".fxml"));
        Scene scene = new Scene(activeWindow); 
        Stage stage = new Stage();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }
    
    public void DisplayMenu(String menuName, MenuButton menu) throws IOException {
        Parent activeWindow = FXMLLoader.load(getClass().getResource("/View/" + menuName + ".fxml"));
        Scene scene = new Scene(activeWindow); 
        Stage stage = new Stage();
        stage = (Stage) menu.getScene().getWindow();
        stage.setScene(scene);
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        stage.show();
    }   
   

    public void DisplayLanguage(Label... args) {

        for (Label arg : args) {
            String label;
            try {
                label = arg.getText();
                label = label.replaceAll("\\s+", "").toLowerCase();
                arg.setText(language.getString(label));
            } catch (RuntimeException re) {
                main.updateLog("error","Could not find " + Locale.getDefault() + "language properties for " + arg + "label: "
                + String.valueOf(re));
            }

        }
    }

    public void DisplayLanguage(Button... args) {

        for (Button arg : args) {
            String buttonText;
            try {
                buttonText = arg.getText();
                buttonText = buttonText.replaceAll("\\s+", "").toLowerCase();
                arg.setText(language.getString(buttonText));
            } catch (RuntimeException re) {
                main.updateLog("error","Could not find " + Locale.getDefault() + "language properties for " + arg + "Button: " 
                        + String.valueOf(re));
                
            }

        }
    }

}
