/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ChecksAndBalances;
import Model.CryptWithMD5;
import Model.Database;
import Model.Display;
import Model.User;
import com.storper.matthew.Main;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class ChangePasswordController implements Initializable {

    private Display display = new Display();
    private Main main = new Main();
    public CryptWithMD5 encrypt = new CryptWithMD5();
    private ChecksAndBalances verify = new ChecksAndBalances();
    private String userName = User.currentUser.getUserName().getValue();
    private int userId = User.currentUser.getUserId();

    @FXML
    private Button backBtn;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button savePasswordBtn;

    @FXML
    public void handleBackButton(ActionEvent event) throws IOException {
        display.DisplayMenu("Home", event);
    }

    @FXML
    public void handleSavePassword(ActionEvent event) throws SQLException, IOException {
        String currentPassword = currentPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();
        boolean authenticated = false;

        if (verify.checkFields(currentPasswordField, newPasswordField, confirmPasswordField)) {

            if (newPassword.equals(confirmPassword)) {

                String statement = "SELECT * FROM user WHERE username=? AND password=?";
                PreparedStatement query = Database.getConnection().prepareStatement(statement);
                query.setString(1, userName);
                //ADD MD5 compare
                query.setString(2, CryptWithMD5.cryptWithMD5(currentPassword));
                ResultSet rs;
                rs = query.executeQuery();

                if (rs.next()) {
                    authenticated = true;
                }

                if (authenticated) {
                    statement = "UPDATE user SET password=? WHERE userId=?";
                    PreparedStatement sql = Database.getConnection().prepareStatement(statement);
                    sql.setString(1, CryptWithMD5.cryptWithMD5(confirmPassword));
                    sql.setInt(2, userId);
                    sql.execute();
                    display.DisplayMessage("passwordChange", "passwordMessage");
                } else {
                    display.DisplayErrorMessage("passwordAuthError", "passwordAuthMessage");
                }

            } else {
                display.DisplayErrorMessage("passwordMatchError", "passwordMatchMessage");
            }

        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

}
