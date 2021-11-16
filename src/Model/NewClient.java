/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Matt
 */
public class NewClient {
    StringProperty year;
    StringProperty month;
    StringProperty count;
    
    public void setClientStats(String year, String month, String count) {
    this.year = new SimpleStringProperty(year);
    this.month = new SimpleStringProperty(month);
    this.count = new SimpleStringProperty(count);
    }
    
    public void setYear(String year) {
    this.year = new SimpleStringProperty(year);
    }
    
    public void setMonth(String month) {
    this.month = new SimpleStringProperty(month);
    }
    
    public void setCount(String count) {
    this.count = new SimpleStringProperty(count);
    }
    
    public StringProperty getYear() {
    return year;
    }
    
    public StringProperty getMonth() {
    return month;
    }
    
    public StringProperty getCount() {
    return count;
    }
    
    
    
}
