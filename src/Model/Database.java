/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import com.storper.matthew.Main;
import Model.User;
import Model.DateTime;
import Model.ChecksAndBalances;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;

/**
 *
 * @author Matt
 */
public class Database {

    public static Connection connection;
    Main main = new Main();
    Display display = new Display();
    User user = new User();
    DateTime convert = new DateTime();
    ChecksAndBalances verify = new ChecksAndBalances();

    //setter
    public static void setConnection() {

        String url = "jdbc:mysql://162.144.181.164:3306/nxd6yz8u_capstonedb?useSSL=false&allowPublicKeyRetrieval=true&interactiveUser=true";
        String username = "nxd6yz8u_user";
        String password = "FTXmFsTm9xhr";
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException se) {
            Display.DisplayErrorMessage("connErrorTitle", "connErrorMessage");
//            updateLog("error", String.valueOf(se));
            Platform.exit();
            se.printStackTrace();
        }
        connection = conn;
    }

    //getter
    public static Connection getConnection() {
        try {
            if (connection.isClosed() || connection == null) {
                setConnection();
                System.out.println("Connection reset");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return connection;
    }

    public void buildAppointmentTable(TableView table) {
        Appointment appointment;
        ResultSet rs;
        ObservableList<Appointment> appointmentArray = FXCollections.observableArrayList();
        int userId = User.currentUser.getUserId();
        String sql = "SELECT * FROM appointment WHERE userId = ?"
                + " ORDER BY start ASC";

        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            query.setInt(1, userId);
            rs = query.executeQuery();
            while (rs.next()) {
                appointment = new Appointment();
                appointment.setAppointment(
                        rs.getString("appointmentId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("type"),
                        rs.getString("url"),
                        convert.dateTimeToString(rs.getTimestamp("start")),
                        convert.dateTimeToString(rs.getTimestamp("end"))
                );                
                appointmentArray.add(appointment);
            }
            query.closeOnCompletion();
            
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        table.setItems(appointmentArray);

    }

    public void buildAppointmentTable(TableView table, int userId) {
        Appointment appointment;
        ResultSet rs;
        ObservableList<Appointment> appointmentArray = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointment WHERE userId = ? "
                + "ORDER BY start ASC";
        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            query.setInt(1, userId);

            rs = query.executeQuery();
            while (rs.next()) {
                appointment = new Appointment();
                appointment.setAppointment(
                        rs.getString("contact"),
                        rs.getString("location"),
                        rs.getString("type"),
                        convert.dateTimeToString(rs.getTimestamp("start"))
                );                
                appointmentArray.add(appointment);
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        table.setItems(appointmentArray);
    }

    public void buildUserTable(TableView table) {
        int permissionLevel = User.currentUser.getPermissionLevel();

        ResultSet rs;
        ObservableList<User> userArray = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user WHERE permissionLevel < ? "
                + "ORDER BY name ASC";

        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            query.setInt(1, permissionLevel);
            User user;

            rs = query.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUser(rs.getInt("userId"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getInt("active"),
                        rs.getInt("permissionLevel"),
                        rs.getString("password"));
                userArray.add(user);
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        } 
        table.setItems(userArray);
    }

    public void buildAppointmentTable(TableView table, String month, String year) {
        Appointment appointment;
        ResultSet rs;
        ObservableList<Appointment> appointmentArray = FXCollections.observableArrayList();
        int userId = User.currentUser.getUserId();
        int monthNum;

        switch (month.toLowerCase()) {
            case "january":
                monthNum = 1;
                break;
            case "february":
                monthNum = 2;
                break;
            case "march":
                monthNum = 3;
                break;
            case "april":
                monthNum = 4;
                break;
            case "may":
                monthNum = 5;
                break;
            case "june":
                monthNum = 6;
                break;
            case "july":
                monthNum = 7;
                break;
            case "august":
                monthNum = 8;
                break;
            case "september":
                monthNum = 9;
                break;
            case "october":
                monthNum = 10;
                break;
            case "november":
                monthNum = 11;
                break;
            default:
                monthNum = 12;
                break;
        }
        String sql = "SELECT * FROM appointment "
                + "WHERE Month(start) = ? AND YEAR(start) = ? AND userId = ? "
                + "ORDER BY start ASC";
        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            query.setInt(1, monthNum);
            query.setInt(2, Integer.parseInt(year));
            query.setInt(3, userId);
            rs = query.executeQuery();

            while (rs.next()) {
                appointment = new Appointment();
                appointment.setAppointment(
                        rs.getString("appointmentId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("type"),
                        rs.getString("url"),
                        convert.dateTimeToString(rs.getTimestamp("start")),
                        convert.dateTimeToString(rs.getTimestamp("end"))
                );                
                appointmentArray.add(appointment);
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        table.setItems(appointmentArray);
    }

    public void buildAppointmentTable(TableView table, int year, int month, int startDay) {
        Appointment appointment;
        ResultSet rs;
        int endDay = startDay + 6;
        ObservableList<Appointment> appointmentArray = FXCollections.observableArrayList();
        int userId = User.currentUser.getUserId();

        String sql = "SELECT * FROM appointment "
                + "WHERE year(start) = ? AND month(start) = ? AND dayofmonth(start) "
                + "BETWEEN ? AND ? "
                + "AND userId = ? ORDER BY start ASC";

        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            query.setInt(1, year);
            query.setInt(2, month);
            query.setInt(3, startDay);
            query.setInt(4, endDay);
            query.setInt(5, userId);

            rs = query.executeQuery();

            while (rs.next()) {
                appointment = new Appointment();
                appointment.setAppointment(
                        rs.getString("appointmentId"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getString("location"),
                        rs.getString("contact"),
                        rs.getString("type"),
                        rs.getString("url"),
                        convert.dateTimeToString(rs.getTimestamp("start")),
                        convert.dateTimeToString(rs.getTimestamp("end"))
                );                
                appointmentArray.add(appointment);
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        table.setItems(appointmentArray);
    }

    public void buildAppointmentTypeTable(TableView table) {
        AppointmentType type = new AppointmentType();
        ResultSet rs;
        ObservableList<AppointmentType> typeArray = FXCollections.observableArrayList();

        String sql = "SELECT Year(start) AS year, monthname(start) AS monthname, "
                + "Month(start) AS month, count(type) AS count "
                + "FROM appointment "
                + "GROUP BY year, monthname, month "
                + "ORDER BY year ASC, month ASC";
        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            rs = query.executeQuery();

            while (rs.next()) {
                type = new AppointmentType();
                type.setAppointmentType(
                        rs.getString("year"),
                        rs.getString("monthname"),
                        rs.getString("count")
                );
                typeArray.add(type);
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        table.setItems(typeArray);
    }

    public void buildNewClientTable(TableView table, String userName, String userId) {
        NewClient clientRecord;
        ResultSet rs;
        ObservableList<NewClient> clientArray = FXCollections.observableArrayList();

        String sql = "SELECT Year(c.createDate) AS year, monthname(c.createDate) AS monthname, month(c.createDate) as monthnumber, COUNT(c.customerId) AS count "
                + "FROM customer AS c JOIN user AS u "
                + "WHERE c.createdBy = ? AND u.userId = ? "
                + "GROUP BY year, monthname, monthnumber "
                + "ORDER BY year ASC, monthnumber ASC";

        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            query.setString(1, userName);
            query.setInt(2, Integer.parseInt(userId));
            rs = query.executeQuery();

            while (rs.next()) {
                clientRecord = new NewClient();
                clientRecord.setClientStats(
                        rs.getString("year"),
                        rs.getString("monthname"),
                        rs.getString("count")
                );
                clientArray.add(clientRecord);
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        table.setItems(clientArray);
    }

    public void buildCustomerTable(TableView table) {
        Customer customer;
        ResultSet rs;
        ObservableList<Customer> customerArray = FXCollections.observableArrayList();

        String sql = "SELECT customerId, customerName, CONCAT_WS(' ' ,address,address2,'\n',city,postalCode,'\n',country) AS Address, phone "
                + "FROM customer AS c "
                + "INNER JOIN "
                + "address AS a "
                + "ON c.addressId = a.addressId "
                + "INNER JOIN "
                + "city AS ci "
                + "ON a.cityId = ci.cityId "
                + "INNER JOIN "
                + "country AS co "
                + "ON ci.countryId = co.countryId;";

        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            rs = query.executeQuery();
            while (rs.next()) {
                customer = new Customer();
                customer.setCustomer(
                        rs.getString("customerId"),
                        rs.getString("customerName"),
                        rs.getString("Address"),
                        rs.getString("phone")
                );
                customerArray.add(customer);
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        table.setItems(customerArray);
    }

    public boolean buildCustomerTable(TableView table, String searchTerm) {
        boolean foundResults = false;
        Customer customer;
        ResultSet rs;
        ObservableList<Customer> customerArray = FXCollections.observableArrayList();

        String sql = "SELECT customerId, customerName, CONCAT_WS(' ' ,address,address2,'\n',city,postalCode,'\n',country) AS Address, phone "
                + "FROM customer AS c "
                + "INNER JOIN "
                + "address AS a "
                + "ON c.addressId = a.addressId "
                + "INNER JOIN "
                + "city AS ci "
                + "ON a.cityId = ci.cityId "
                + "INNER JOIN "
                + "country AS co "
                + "ON ci.countryId = co.countryId "
                + "WHERE c.customerName LIKE ? ;";

        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            query.setString(1, "%" + searchTerm + "%");
            rs = query.executeQuery();
            while (rs.next()) {
                customer = new Customer();
                customer.setCustomer(
                        rs.getString("customerId"),
                        rs.getString("customerName"),
                        rs.getString("Address"),
                        rs.getString("phone")
                );
                customerArray.add(customer);
            }
           query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        if (customerArray.size() > 0) {
            table.setItems(customerArray);
            foundResults = true;
        }
        return foundResults;
    }

    public void buildUpcomingTable(TableView tableName) {
        Appointment appointment;
        ResultSet rs;
        ObservableList<Appointment> appointmentArray = FXCollections.observableArrayList();
        boolean alertUser = false;

        String sql = "SELECT * FROM appointment"
                + " WHERE (year(start) = year(CURRENT_DATE()) AND month(start) = month(CURRENT_DATE()) "
                + "AND dayofmonth(start) >= dayofMonth(CURRENT_DATE())) "
                + "AND userId = ?";

        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            query.setInt(1, User.currentUser.getUserId());
            rs = query.executeQuery();

            while (rs.next()) {
                appointment = new Appointment();
                appointment.setAppointment(
                        rs.getString("contact"),
                        rs.getString("location"),
                        rs.getString("type"),
                        convert.dateTimeToString(rs.getTimestamp("start"))
                );
                appointmentArray.add(appointment);

                String tempDate = convert.dateToString(rs.getTimestamp("start")).trim();
                String currentDate = convert.localDateString();
                System.out.println(tempDate + "\n" + currentDate);
                if (tempDate.equals(currentDate)) {
                    String tempTime = convert.timeToString(rs.getTimestamp("start"));
                    String currentTime = convert.localTimeString();

                    String tempHour = tempTime.split(":")[0];
                    String currentHour = currentTime.split(":")[0];

                    if (tempHour.equals(currentHour)) {
                        int tempMinute = Integer.parseInt(tempTime.split(":")[1]);
                        int currentMinute = Integer.parseInt(currentTime.split(":")[1]);

                        if ((tempMinute - currentMinute) >= 0 && (tempMinute - currentMinute) <= 15) {
                            alertUser = true;
                        }
                    }

                }
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        tableName.setItems(appointmentArray);
        if (alertUser) {
            display.DisplayMessage("meetingWindow", "meetingMessage");
        }
    }

    public void deleteRecord(String tableName, String id) {
        String statement;
        if (tableName.equals("appointment")) {
            statement = "DELETE FROM appointment"
                    + " WHERE customerId = " + id;
        } else {
            statement = "DELETE FROM " + tableName
                    + " WHERE " + tableName + "Id = " + id;
        }
        try {
            PreparedStatement query = getConnection().prepareStatement(statement);
            query.execute();
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
    }

    public void deleteAppointment(String tableName, String appointmentId) {
        String statement = "DELETE FROM appointment"
                + " WHERE appointmentId = " + appointmentId;
        try {
            PreparedStatement query = getConnection().prepareStatement(statement);
            query.execute();
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
    }

    public ResultSet getUsers() {

        String sql = "SELECT userId, userName FROM user";
        ResultSet rs = null;

        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            rs = query.executeQuery();
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }

        return rs;
    }

    public int generateIds(String tableName) {
        String sql = "SELECT MAX(" + tableName + "Id) AS currentId FROM " + tableName;
        int newId = 0;
        try {
            PreparedStatement query = getConnection().prepareStatement(sql);
            ResultSet rs = query.executeQuery();
            if (rs.next()) {
                newId = rs.getInt("currentId") + 1;
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        return newId;
    }

    public void insertUser(String name, String userName, String password, int active, int permissionLevel, String currentUser) {

        String sql = "INSERT INTO user (name, userName, password, active, permissionLevel, createDate, createdBy, lastUpdate, lastUpdatedBy) "
                + "VALUES(?, ?, ?, ?, ?, CURRENT_TIMESTAMP,?, CURRENT_TIMESTAMP,?)";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, userName);
            statement.setString(3, password);
            statement.setInt(4, active);
            statement.setInt(5, permissionLevel);
            statement.setString(6, currentUser);
            statement.setString(7, currentUser);
            statement.execute();
            statement.closeOnCompletion();
            System.out.println("added!");
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
            System.out.println(se.toString());
            System.out.println("not successful!");
        }
    }

    public void updateUser(int userId, String name, String userName, String password, int active, int permissionLevel, String currentUser) {
        String sql = "Update user SET name = ?, userName = ?, password = ?, active = ?, permissionLevel = ?,"
                + "  lastUpdate = CURRENT_TIMESTAMP, lastUpdatedBy = ? "
                + "WHERE userId = ?";

        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, userName);
            statement.setString(3, password);
            statement.setInt(4, active);
            statement.setInt(5, permissionLevel);
            statement.setString(6, currentUser);
            statement.setInt(7, userId);
            statement.execute();
            statement.closeOnCompletion();
            System.out.println("Updated!");
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
            System.out.println(se.toString());
            System.out.println("not successful!");
        }
    }

    public void InsertIntoDatabase(String tablename, String... args) throws SQLException {

        int number = 0;
        String sqlTable = "INSERT INTO " + tablename + " VALUES (";
        String sql = "";
        String special = ", ";

        for (String arg : args) {
            number++;
            if (number == args.length) {
                special = ")";
            }
            try {
                if (Integer.parseInt(arg) >= 0) {
                    sql = sql + arg + special;
                }
            } catch (NumberFormatException e) {
                if ((arg.toLowerCase().contentEquals("null")) || (arg.toLowerCase().contentEquals("current_timestamp"))) {
                    sql = sql + arg + special;
                } else {
                    sql = sql + "'" + arg + "'" + special;
                }
            }
        }

        String statement = sqlTable + sql;
        try {
            PreparedStatement query = getConnection().prepareStatement(statement);
            query.execute();
            query.closeOnCompletion();

        } catch (SQLException se) {
            main.updateLog("error", se.toString());

        }

    }

    public boolean noAppointmentConflict(Timestamp startTime, Timestamp endTime, String appointmentId) {
        boolean noConflict = true;
        System.out.println(startTime + "\n" + endTime);
        String statement = "Select * FROM appointment "
                + "WHERE userId = ? "
                + "AND appointmentId != ? "
                + "AND(timestamp(start) BETWEEN timestamp(?) AND timestamp(?) "
                + "OR timestamp(end) BETWEEN timestamp(?) AND timestamp(?))";

        try {
            ResultSet rs;
            PreparedStatement query = getConnection().prepareStatement(statement);
            System.out.println(appointmentId);

            query.setInt(1, User.currentUser.getUserId());
            query.setInt(2, Integer.parseInt(appointmentId));
            query.setTimestamp(3, startTime);
            query.setTimestamp(4, endTime);
            query.setTimestamp(5, startTime);
            query.setTimestamp(6, endTime);

            rs = query.executeQuery();

            if (rs.next()) {
                System.out.println(rs.getString("appointmentId"));
                noConflict = false;
                main.updateLog("alert", "Appointment scheduling conflict for date and time of: " + startTime
                        + " to " + endTime);
                display.DisplayErrorMessage("notAvailable", "notAvailableMessage");
            }
            query.closeOnCompletion();
        } catch (SQLException se) {
            noConflict = false;
            main.updateLog("error", se.toString());
        }
        return noConflict;
    }

    public ResultSet retrieveRecordById(String id) {

        ResultSet rs = null;
        String statement = "SELECT * "
                + "FROM customer AS c "
                + "INNER JOIN "
                + "address AS a "
                + "ON c.addressId = a.addressId "
                + "INNER JOIN "
                + "city AS ci "
                + "ON a.cityId = ci.cityId "
                + "INNER JOIN "
                + "country AS co "
                + "ON ci.countryId = co.countryId"
                + " WHERE c.customerId = " + id;
        try {
            PreparedStatement query = getConnection().prepareStatement(statement);
            rs = query.executeQuery();
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        return rs;
    }

    public ResultSet retrieveRecordById(String tableName, String id) {

        ResultSet rs = null;
        String statement = "SELECT * FROM " + tableName
                + " WHERE " + tableName + "id = " + id;

        try {
            PreparedStatement query = getConnection().prepareStatement(statement);
            rs = query.executeQuery();
            query.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
        return rs;
    }

    public void updateAddress(String address, String address2, String postalCode, String phone, String id) {

        String statement = "UPDATE address SET address = ?, address2 = ?, postalCode = ?, phone = ?, lastUpdate = CURRENT_TIMESTAMP "
                + "WHERE addressId = ?";
        try {
            PreparedStatement sql = getConnection().prepareStatement(statement);
            sql.setString(1, address);
            sql.setString(2, address2);
            sql.setString(3, postalCode);
            sql.setString(4, phone);
            sql.setInt(5, Integer.parseInt(id));
            sql.execute();
            sql.closeOnCompletion();;
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }

    }

    public void updateAddress(String address, String postalCode, String phone, String id) {

        String statement = "UPDATE address SET address = ?, postalCode = ?, phone = ?, lastUpdate = CURRENT_TIMESTAMP "
                + "WHERE addressId = ?";
        try {
            PreparedStatement sql = getConnection().prepareStatement(statement);
            sql.setString(1, address);
            sql.setString(2, postalCode);
            sql.setString(3, phone);
            sql.setInt(4, Integer.parseInt(id));
            sql.execute();
            sql.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }

    }

    public void updateAppointment(String title, String location, String description, String contact, String type,
            String url, Timestamp start, Timestamp end, String userName, String appointmentId) {

        String statement = "UPDATE appointment SET title = ?, location = ?, description = ?, contact = ?, type = ?"
                + ", url = ?, start = ?, end = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ? "
                + "WHERE appointmentId = ?";

        try {
            PreparedStatement sql = getConnection().prepareStatement(statement);
            sql.setString(1, title);
            sql.setString(2, location);
            sql.setString(3, description);
            sql.setString(4, contact);
            sql.setString(5, type);
            sql.setString(6, url);
            sql.setTimestamp(7, start);
            sql.setTimestamp(8, end);
            sql.setString(9, userName);
            sql.setInt(10, Integer.parseInt(appointmentId));
            sql.execute();
            sql.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
    }

    public void insertAppointment(String title, String location, String description, String contact, String type,
            String url, Timestamp start, Timestamp end, String userName, String appointmentId) {

        String statement = "INSERT INTO appointment VALUES(appointmentId = ?, customerId = ?, userId = ?, title = ?, description = ?, location = ? , contact = ?, type = ?"
                + ", url = ?, start = ?, end = ?, lastUpdate = CURRENT_TIMESTAMP, lastUpdateBy = ?)";

        try {
            PreparedStatement sql = getConnection().prepareStatement(statement);
            sql.setString(1, title);
            sql.setString(2, description);
            sql.setString(3, location);
            sql.setString(4, contact);
            sql.setString(5, type);
            sql.setString(6, url);
            sql.setTimestamp(7, start);
            sql.setTimestamp(8, end);
            sql.setString(9, userName);
            sql.setInt(10, Integer.parseInt(appointmentId));
            sql.execute();
            sql.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
    }

    public void updateCity(String city, String id) {
        String statement = "UPDATE city SET city = ?, lastUpdate = CURRENT_TIMESTAMP "
                + " WHERE cityId = ?";
        try {
            PreparedStatement sql = getConnection().prepareStatement(statement);
            sql.setString(1, city);
            sql.setInt(2, Integer.parseInt(id));
            sql.execute();
            sql.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
    }

    public void updateCountry(String country, String id) {
        String statement = "UPDATE country SET country = ?, lastUpdate = CURRENT_TIMESTAMP "
                + " WHERE countryId = ?";
        try {
            PreparedStatement sql = getConnection().prepareStatement(statement);
            sql.setString(1, country);
            sql.setInt(2, Integer.parseInt(id));
            sql.execute();
            sql.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }
    }

    public void updateCustomer(String customerName, String id) {

        String statement = "UPDATE customer SET customerName = ?, lastUpdate = CURRENT_TIMESTAMP"
                + " WHERE customerId = ?";

        try {
            PreparedStatement sql = getConnection().prepareStatement(statement);
            sql.setString(1, customerName);
            sql.setInt(2, Integer.parseInt(id));
            sql.execute();
            sql.closeOnCompletion();
        } catch (SQLException se) {
            main.updateLog("error", se.toString());
        }

    }

}
