/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import com.storper.matthew.Main;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Matt
 */
public class User {

    public static User currentUser = new User();
    public static User childUser = new User();
    private int active;
    private int permissionLevel;
    private int userId;
    private StringProperty userIdStringProperty;
    private StringProperty userName;
    private StringProperty name;
    private static ArrayList permissionsList;
    private String password;
    HashMap<Integer, String> permissions = new HashMap<Integer, String>();

    public void setUser(int userId, String name, String userName, int active, int permissionLevel, String password) {
        setUserId(userId);
        setName(name);
        setUserName(userName);
        this.active = active;
        this.permissionLevel = permissionLevel;
        setPassword(password);

        permissions.put(1, "USER");
        permissions.put(2, "ADMIN");
        permissions.put(3, "SUPER ADMIN");
        permissions.put(4, "OWNER");
    }

    //setters
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUserIdStringProperty(int userId) {
        String userIdString = String.valueOf(userId);
        this.userIdStringProperty = new SimpleStringProperty(userIdString);
    }

    public void setUserName(String username) {
        this.userName = new SimpleStringProperty(username);
    }

    public void setUserDetails(int userId, String username) {
        this.userId = userId;
        this.userName = new SimpleStringProperty(username);
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public void setPermissionLevel(int permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    private void setPassword(String password) {
        if (password != null) {
            this.password = CryptWithMD5.cryptWithMD5(password);
        }
    }

    //getters
    public int getUserId() {
        return userId;
    }

    public StringProperty getUserIdStringProperty() {
        return userIdStringProperty;
    }

    public StringProperty getUserName() {
        return userName;
    }

    public StringProperty getName() {
        return name;
    }

    public StringProperty getActiveStatusString() {
        String status;

        if (this.active == 1) {
            status = "yes";
        } else {
            status = "no";
        }
        return new SimpleStringProperty(status);
    }

    public StringProperty getPermissionLevelString() {
        String permissionString = permissions.get(permissionLevel);
        return new SimpleStringProperty(permissionString);
    }

    public int getActive() {
        return active;
    }

    public int getPermissionLevel() {
        return permissionLevel;
    }

    public ArrayList getPermissionList() {
        permissionsList = new ArrayList();
        int currentPermissionLevel = getPermissionLevel();

        for (int i : permissions.keySet()) {
            if (i <= currentPermissionLevel) {
                permissionsList.add(permissions.get(i));
            }
        }
        return permissionsList;
    }

    public String getPassword() {
        return password;
    }

    public int convertStringToPermission(String userType) {
        userType = userType.toUpperCase();
        int stringToPermission = 0;

        if (userType.equals("USER")) {
            stringToPermission = 1;
        } else if (userType.equals("ADMIN")) {
            stringToPermission = 2;
        } else if (userType.equals("SUPER ADMIN")) {
            stringToPermission = 3;
        } else if (userType.equals("OWNER")) {
            stringToPermission = 4;
        }
        return stringToPermission;
    }

    public int convertStringToActive(String activeType) {
        activeType = activeType.toUpperCase();
        int stringToActive = 0;
        if (activeType.equals("YES")) {
            stringToActive = 1;
        }
        return stringToActive;
    }

}
