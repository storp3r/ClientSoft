/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 *
 * @author Matt
 */
public class ChecksAndBalances {

    Display display = new Display();

    public boolean checkFields(TextField... args) {
        boolean clearedCheck = false;
        for (TextField arg : args) {
            String input;
            input = arg.getText();
            if (input.isEmpty()) {
                display.DisplayErrorMessage("infoMissingTitle", "infoMissingMessage");
                System.out.println("ran check");
                clearedCheck = false;
                break;
            } else {
                clearedCheck = true;
            }
        }
        return clearedCheck;
    }

    public boolean checkIntegers(TextField... args) {
        boolean clearedInt = false;

        for (TextField arg : args) {
            String input = arg.getText();
            Long newLong;

            try {
                newLong = Long.valueOf(input);
            } catch (NumberFormatException e) {
                newLong = -1L;
                arg.setStyle("-fx-background-color: pink");
            }

            if (newLong != -1L) {
                clearedInt = true;
            } else {
                clearedInt = false;
            }

        }
        return clearedInt;
    }

    public boolean dateCheck(LocalDate currentDate, LocalDate selectedDate, DatePicker pickerName) {
        boolean dateNotOkay = false;

        if (selectedDate != null) {
            if (currentDate.isBefore(selectedDate)) {
                dateNotOkay = true;
            } else {
                dateNotOkay = false;
                display.DisplayErrorMessage("invalidDate", "invalidDateMessage");
            }            
        }

        return dateNotOkay;
    }

    public boolean dateNotEmpty(DatePicker dateField) {
        boolean notEmpty = false;
        if (dateField.getValue() != null) {
            notEmpty = true;
        } else {
            display.DisplayErrorMessage("invalidDate", "invalidDateMessage");
        }
        return notEmpty;
    }

    public void hourUpdate(ComboBox startHour, ComboBox startMinutes, ComboBox endHour, ComboBox endMinutes) { 
        int i = 0;
        int startTime = Integer.parseInt(startHour.getValue().toString());

        if (startMinutes.getValue() == startMinutes.getItems().get(
                startMinutes.getItems().size() - 1)) {
            minuteUpdate(startHour, startMinutes, endHour, endMinutes);
            endHour.getItems().clear();
            while (i < startHour.getItems().size()) {
                Object newTime = startHour.getItems().get(i);

                int newNum = Integer.parseInt(newTime.toString());
                if (startTime < 5) {
                    if (newNum < 5 && newNum > startTime) {
                        endHour.getItems().add(newTime);
                    }
                } else if (newNum > startTime || newNum < 5) {
                    endHour.getItems().add(newTime);
                }
                i++;
            }

            endHour.getSelectionModel().selectNext();
            endMinutes.getSelectionModel().selectFirst();

        } else {

            Object storedHour = null;
            Object storedMinutes = null;
            int endTime = 0;
            if (endHour.getValue() != null ) {
                storedHour = endHour.getValue();
                endTime = Integer.parseInt(endHour.getValue().toString());
            }
            if (endMinutes.getValue() != null) {
                storedMinutes = endMinutes.getValue();
            }

            endHour.getItems().clear();
            minuteUpdate(startHour, startMinutes, endHour, endMinutes);

            while (i < startHour.getItems().size()) {
                Object newTime = startHour.getItems().get(i);

                int newNum = Integer.parseInt(newTime.toString());

                if (startTime < 5) {
                    if (newNum < 5 && newNum >= startTime) {
                        endHour.getItems().add(newTime);
                    }
                } else if (newNum >= startTime || newNum < 5) {
                    endHour.getItems().add(newTime);
                }
                i++;
            }
            endHour.getItems().add(5);
            System.out.println(startTime);
            if ((storedHour != null) && endTime >= startTime) {
                endHour.getSelectionModel().select(storedHour);
            }

            if (storedMinutes != null) {
                endMinutes.getSelectionModel().select(storedMinutes);
            }
        }
        
    }

    public void minuteUpdate(ComboBox startHour, ComboBox startMinutes, ComboBox endHour, ComboBox endMinutes) { //TODO FIX END OF DAY

        endMinutes.getItems().clear();

        int i = 0;
        while (i < startMinutes.getItems().size()) {

            int newNum = Integer.parseInt(startMinutes.getItems().get(i).toString());

            if ((endHour.getValue() == startHour.getValue())
                    && startMinutes.getValue() != null) {
                int startMinute = Integer.parseInt(startMinutes.getValue().toString());
                if (newNum > startMinute) {
                    endMinutes.getItems().add(startMinutes.getItems().get(i).toString());
                }
            } else {
                endMinutes.getItems().add(startMinutes.getItems().get(i).toString());
            }
            i++;
        }
        

    }
    
    public void noResultsFound() {
        display.DisplayErrorMessage("No Results Found", "Check your search parameters and try again");
    }
    
    

    public boolean nullBoxCheck(ComboBox... args) {
        boolean notNull = true;
        for (ComboBox arg : args) {
            Object value = arg.getValue();

            if (value == null) {
                notNull = false;
            }
        }
        return notNull;
    }

    public void setCalendar(DatePicker pickerName) {
        //This lambda expression allows disablement of specific dates
        pickerName.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
                
                if(date.getDayOfWeek() == DayOfWeek.SUNDAY || date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                    setDisable(true);
                    setVisible(false);
                    
                }
                
            }
        });

    }
    
    public void overrideStartHour() {
        
    }
    
    public void setCalendar(DatePicker pickerName, String type) {
        pickerName.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {

                if(!(getStyleClass().contains("next-month") || getStyleClass().contains("previous-month"))) {
                setStyle("-fx-background-color: #50514F; -fx-text-fill: #FFF");
                } else {
                    setStyle("-fx-background-color: #A9A9A9; -fx-text-fill: #FFF");
                }
                
                if(date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                    setDisable(true);
                    setVisible(false);
                    
                }
                
            }
        });
    }

    public void setLabels(ComboBox startHour, ComboBox endHour, Label label1, Label label2) {
        if (startHour.getValue() != null) {
            int startTime = Integer.parseInt(startHour.getValue().toString());
            if (startTime >= 9 && startTime < 12) {
                label1.setText("AM");
            } else {
                label1.setText("PM");
            }
        } else {
            label1.setText("");
        }

        if (endHour.getValue() != null) {
            int endTime = Integer.parseInt(endHour.getValue().toString());
            if (endTime >= 9 && endTime < 12) {
                label2.setText("AM");
            } else {
                label2.setText("PM");
            }
        } else {
            label2.setText("");
        }
    }

}
