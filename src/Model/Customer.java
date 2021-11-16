/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;
import javafx.beans.property.*;
/**
 *
 * @author Matt
 */
public class Customer {
    private StringProperty id;
    private StringProperty name;
    private StringProperty address;
    private StringProperty phone;
    
    
    
    public void setCustomer(String id, String name, String address, String phone) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.address = new SimpleStringProperty(address);
        this.phone = new SimpleStringProperty(phone);  

    } 
    
    
    
    
    public void setCustId(String id) {
        this.id = new SimpleStringProperty(id);
    }
    
    public void setCustName(String name) {
        this.name = new SimpleStringProperty(name);
    }
    
    public void setCustAddress(String address) {
        this.address = new SimpleStringProperty(address);
    }
    
    public void setCustPhone(String phone) {
        this.phone = new SimpleStringProperty(phone);
    }
    
    
    
    
    public StringProperty getCustId() {
        return id;
    }
    
    public StringProperty getCustName() {
        return name;
    }
    
    public StringProperty getCustAddress() {
        return address;
    }
    
    
    public StringProperty getCustPhone() {
        return phone;
    }
    
    
    
    
    
    
}
