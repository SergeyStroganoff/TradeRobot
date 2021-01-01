package com.stroganov;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFromString {

    public static LocalDate GetDateFromString(String stringDate) {

        LocalDate date;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yy");
        date = LocalDate.parse(stringDate, dtf);
        return date;

        //  01/05/16
    }
}
