/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Matt
 */
public class DateTime {

    public static LocalDate stringToDate(String stringDate) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, df);
        return date;
    }

    public static LocalTime stringToTime(String stringTime) {
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("kk:mm");
        LocalTime time = LocalTime.parse(stringTime, tf);
        return time;
    }

    public static String dateToString(Timestamp date) {

        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zdt = date.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime ldt = zdt.withZoneSameInstant(zone);
        LocalDate tempDate = ldt.toLocalDate();

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = tempDate.format(df);
        return dateString;
    }
    
    public static String localDateString() {
    Timestamp current = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = df.format(current);
        return dateString;
    }
    
    public static String localTimeString() {
    Timestamp current = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat tf = new SimpleDateFormat("kk:mm");
        String timeString = tf.format(current);
        return timeString;
    }

    public static String timeToString(Timestamp time) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zdt = time.toLocalDateTime().atZone(ZoneId.of("UTC"));
        ZonedDateTime ldt = zdt.withZoneSameInstant(zone);
        LocalTime tempTime = ldt.toLocalTime();
        DateTimeFormatter tf = DateTimeFormatter.ofPattern("kk:mm");
        String timeString = tempTime.format(tf);
        return timeString;
    }
    
    public static String dateTimeToString(Timestamp dateTime) {
    String newDate = dateToString(dateTime);
    String newTime = timeToString(dateTime);
    return newTime + "\n" + newDate;
    }

    public static Timestamp convertToUTC(LocalDate date, LocalTime time) {
        
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm");
        LocalDateTime localDateTime = LocalDateTime.parse(date + " " + time, dateTimeFormatter);
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = localDateTime.atZone(zone);
        ZonedDateTime UTCDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        localDateTime = UTCDateTime.toLocalDateTime();
        
        return Timestamp.valueOf(localDateTime);
    }

    private static Timestamp convertToUTC(LocalDateTime dateTime) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = dateTime.atZone(zone);
        ZonedDateTime UTCDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        dateTime = UTCDateTime.toLocalDateTime();
        Timestamp timestampDateTime = Timestamp.valueOf(dateTime);
        return timestampDateTime;
    }
}
