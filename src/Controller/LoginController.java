/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.CryptWithMD5;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import Model.Database;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import Model.Display;
import Model.User;
import com.storper.matthew.Main;
import javafx.scene.control.PasswordField;

/**
 * FXML Controller class
 *
 * @author Matt
 */
public class LoginController implements Initializable {

    private final Main main = new Main();

    private final ResourceBundle language = ResourceBundle.getBundle("lang", Locale.getDefault());

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginWindowLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    Display display = new Display();
    Database database = new Database();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        loginWindowLabel.setText(language.getString("loginWindowText"));
        usernameLabel.setText(language.getString("usernameLabel"));
        passwordLabel.setText(language.getString("passwordLabel"));
        loginButton.setText(language.getString("loginButtonText"));

    }

    private ResultSet rs;
    private String sql;
    private PreparedStatement query;
    public static String username;

    @FXML
    void handleLoginButtonAction(ActionEvent event) throws SQLException, IOException {

        username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (!(username.isEmpty()) && !(password.isEmpty())) {

            sql = "SELECT * FROM user WHERE username=? AND password=?";
            query = Database.getConnection().prepareStatement(sql);
            query.setString(1, username);
            query.setString(2, CryptWithMD5.cryptWithMD5(password));
            System.out.println(CryptWithMD5.cryptWithMD5(password));
            rs = query.executeQuery();

            if (rs.next()) {
                if (rs.getInt("active") != 0) {
                    User.currentUser.setUser(rs.getInt("userId"),
                            rs.getString("name"),
                            rs.getString("userName"),
                            rs.getInt("active"),
                            rs.getInt("permissionLevel"),
                            null);
                    display.DisplayMenu("Home", event);
                    main.updateLog("User '" + username + "' successfully logged in!", "info");
                } else{
                    Display.DisplayErrorMessage("accountNotActive", "accountNotActiveMessage");
                }

            } else {
                main.updateLog("Attempted log in failed: Username '" + username + "' and password "
                        + "combination was not found.", "alert");
                Display.DisplayErrorMessage("notFoundTitle", "notFoundMessage");

            }
        } else {
            Display.DisplayErrorMessage("infoMissingTitle", "infoMissingMessage");

        }
    }
}
