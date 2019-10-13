package com.automationtasks.testHelpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class TestHelper {
    public static String convertEOLtoWinFormat(String dataString){
        return dataString.replaceAll("\\r\\n", "\n").replaceAll("\\r", "\n");
    }

    public static String transformFileToString(String filePath){
        String xmlToString = "";

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(filePath));
            xmlToString = new String(encoded, StandardCharsets.US_ASCII);
        } catch (IOException e) {
            System.out.printf("Error reading file %s", e.getMessage());
        }

        return xmlToString;
    }

    public static String transformHttpContentToString(InputStream httpContent){
        String httpContentToString = "";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(httpContent, StandardCharsets.US_ASCII))) {
            httpContentToString = br.lines().collect(Collectors.joining(System.lineSeparator()));
        } catch (IOException e) {
            System.out.printf("Error converting received data %s", e.getMessage());
        }

        return httpContentToString;
    }
}
