package ru.market.domain.converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateTimeConverter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeConverter(){}

    public static LocalDate convertToLocalDate(String dateStr){
        if(dateStr == null || "".equals(dateStr)){
            return LocalDate.now();
        }

        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    public static String convertToDateStr(LocalDate date){
        return date.format(DATE_FORMATTER);
    }

    public static LocalTime convertToLocalTime(String timeStr){
        if(timeStr == null || "".equals(timeStr)){
            return LocalTime.now();
        }

        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    public static String convertToTimeStr(LocalTime time){
        return time.format(TIME_FORMATTER);
    }
}
