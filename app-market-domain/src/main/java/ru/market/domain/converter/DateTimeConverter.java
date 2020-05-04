package ru.market.domain.converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

final class DateTimeConverter {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    private DateTimeConverter(){}

    static LocalDate convertToLocalDate(String dateStr){
        if(dateStr == null || "".equals(dateStr)){
            return LocalDate.now();
        }

        return LocalDate.parse(dateStr, DATE_FORMATTER);
    }

    static String convertToDateStr(LocalDate date){
        return date.format(DATE_FORMATTER);
    }

    static LocalTime convertToLocalTime(String timeStr){
        if(timeStr == null || "".equals(timeStr)){
            return LocalTime.now();
        }

        return LocalTime.parse(timeStr, TIME_FORMATTER);
    }

    static String convertToTimeStr(LocalTime time){
        return time.format(TIME_FORMATTER);
    }
}
