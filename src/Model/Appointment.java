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
public class Appointment {

    private StringProperty appointmentId;
    private StringProperty title;
    private StringProperty description;
    private StringProperty location;
    private StringProperty contact;
    private StringProperty meetingType;
    private StringProperty startDateTime;
    private StringProperty endDateTime;
    private StringProperty url;

    public void setAppointment(String appointmentId, String title, String description, String location
    , String customerName, String meetingType, String url, String startDateTime, String endDateTime ) {
        this.appointmentId = new SimpleStringProperty(appointmentId);
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleStringProperty(location);
        this.contact = new SimpleStringProperty(customerName);
        this.meetingType = new SimpleStringProperty(meetingType);
        this.startDateTime = new SimpleStringProperty(startDateTime);
        this.endDateTime = new SimpleStringProperty(endDateTime);
        this.url = new SimpleStringProperty(url);
    }
    
    public void setAppointment(String customerName, String location, String meetingType, String startDateTime) {
        this.contact = new SimpleStringProperty(customerName);
        this.location = new SimpleStringProperty(location);
        this.meetingType = new SimpleStringProperty(meetingType);
        this.startDateTime = new SimpleStringProperty(startDateTime);
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = new SimpleStringProperty(appointmentId);
    }

    public void setTitle(String title) {
        this.title = new SimpleStringProperty(title);
    }

    public void setDescription(String description) {
        this.description = new SimpleStringProperty(description);
    }

    public void setLocation(String location) {
        this.location = new SimpleStringProperty(location);
    }

    public void setContact(String customerName) {
        this.contact = new SimpleStringProperty(customerName);
    }

    public void setType(String meetingType) {
        this.meetingType = new SimpleStringProperty(meetingType);
    }

    public void setDateTime(String startDateTime) {
        this.startDateTime = new SimpleStringProperty(startDateTime);
    }

    public void setEndTime(String endDateTime) {
        this.endDateTime = new SimpleStringProperty(endDateTime);
    }

    public void setUrl(String url) {
        this.url = new SimpleStringProperty(url);
    }

    public StringProperty getAppointmentId() {
        return this.appointmentId;
    }

    public StringProperty getTitle() {
        return this.title;
    }

    public StringProperty getDescription() {
        return this.description;
    }

    public StringProperty getLocation() {
        return this.location;
    }

    public StringProperty getContact() {
        return this.contact;
    }

    public StringProperty getMeetingType() {
        return this.meetingType;
    }

    public StringProperty getStart() {
        return this.startDateTime;
    }

    public StringProperty getEnd() {
        return this.endDateTime;
    }

    public StringProperty getUrl() {
        return this.url;
    }
}
