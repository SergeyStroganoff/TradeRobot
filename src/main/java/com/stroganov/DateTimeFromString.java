package com.stroganov;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFromString {

    public static LocalDate GetDateFromString(String stringDate) {

        LocalDate date;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
        date = LocalDate.parse(stringDate, dtf);
        return date;

        //  01/05/16
    }


    public static LocalTime GetTimeFromString(String string) {

        LocalTime time;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss");
        time = LocalTime.parse(string, dateTimeFormatter);
        return time;
    }
}
