package com.automationtasks.Helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DatesHelper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String transformLocalDateToString(LocalDate date) {
        return date.format(FORMATTER);
    }

    public static List<String> listOfDatesBetweenStartAndEnd(LocalDate startDate, LocalDate endDate){
        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);

        List<String> listOfDates =  IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startDate::plusDays)
                .map(Object -> Object.format(FORMATTER))
                .collect(Collectors.toList());

        return listOfDates;
    }
}
