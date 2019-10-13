package com.automationtasks;

import java.time.LocalDate;
import java.time.LocalTime;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Main {

        public static void main(String[] args){
            LocalTime startTime = LocalTime.now();

            LocalDate startDate = LocalDate.of(2000, 10, 10);
            LocalDate endDate = LocalDate.of(2000, 10, 15);
            String valuteCharCode = "USD";

//            //Task #4
//            System.out.println(BnmWebService.getValCurs(startDate));
//            //Task #5
//            System.out.println(BnmWebService.getValuteByCharCode(startDate, valuteCharCode));
//            //Task #6
            System.out.println(BnmWebService.getMaxValute(startDate, endDate, valuteCharCode));
            LocalTime endTime = LocalTime.now();
            System.out.printf("Elapsed: %d min %d sec", startTime.until(endTime, MINUTES), startTime.until(endTime, SECONDS));
        }
}
