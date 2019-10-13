package com.automationtasks;

import com.automationtasks.testHelpers.TestHelper;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;

import static org.junit.Assert.*;

public class BnmWebServiceTest {
    private static final String EXCHANGE_XML_FILE_PATH = "src/test/java/com/automationtasks/staticTestData/exchange_rates_10_10_2017.xml";
    private static final String RANDOM_XML_FILE_PATH = "src/test/java/com/automationtasks/staticTestData/breakfast.xml";
    private static final String BNM_URL = "https://www.bnm.md/en/official_exchange_rates?get_xml=1&date=";

    @Test
    public void generateRequestLinkForBnm(){
        String cursDate = "10.10.2017";
        String expectedBnmLink = BNM_URL + cursDate;
        String actualBnmLink;

        actualBnmLink = BnmWebService.generateRequestLink(cursDate);

        assertEquals(expectedBnmLink, actualBnmLink);
    }

    @Test
    public void getContentFromServerThatReturnsValidXml(){
        String expectedDataFromLocalFile = TestHelper.transformFileToString(EXCHANGE_XML_FILE_PATH);
        String cursDate = "10.10.2017";
        String bnmRequestUrl = BNM_URL + cursDate;
        String actualDataFromServer;

        InputStream dataFromService = BnmWebService.getContentFromServer(bnmRequestUrl);
        actualDataFromServer = TestHelper.transformHttpContentToString(dataFromService);

        assertEquals(expectedDataFromLocalFile, TestHelper.convertEOLtoWinFormat(actualDataFromServer));
    }

    @Test(expected = RuntimeException.class)
    public void getContentFromServerThatReturnsError(){
        /* TODO: Create negative test to verify that getContentFromServer method handles correctly response
            from server that != 200 */
        //QUESTION: This test will pass only in case if RESPONSE != 200. Which basically means each time either requestToServerReturnsValidCursXml
        // or requestToServerReturnsError will be failed. Is this acceptable?
        //MY ANSWER: As a parameter I send a link that leads to page with status 404
        //QUESTION: Should negative test be reflected in test name?
        //Is it enough to mention error in annotation or appropriate assert should be made?
        String linkToPageWithError = "https://httpstat.us/404";

        BnmWebService.getContentFromServer(linkToPageWithError);
    }

    @Test
    public void deserializeValidContentToValCurs() {
        //TODO: Create test to verify that deserialize method deserializes entity correctly. HttpResponse to be mocked
        //QUESTION - Which method to verify? deserialize or getValCurs? pretty much the same
        InputStream fileContent = null;

        try {
            fileContent = new FileInputStream(EXCHANGE_XML_FILE_PATH);
        } catch (FileNotFoundException e) {
            System.out.printf("Error finding file %s", e.getMessage());
        }

        ValCurs valCursObj = BnmWebService.deserialize(fileContent, ValCurs.class);

        assertEquals(ValCurs.class, valCursObj.getClass());
        assertNotNull(valCursObj);
    }

    @Test
    public void deserializeInvalidContentToValCurs(){
        InputStream fileContent = null;

        try {
            fileContent = new FileInputStream(RANDOM_XML_FILE_PATH);
        } catch (FileNotFoundException e) {
            System.out.printf("Error finding file %s", e.getMessage());
        }

        ValCurs valCursObj = BnmWebService.deserialize(fileContent, ValCurs.class);

        assertNull(valCursObj);
    }

    @Test
    public void getValCurs() {
        //TODO: Create test to verify that getVarCurs method returns ValCurs object. HttpResponse to be mocked
        //QUESTION - Which method to verify? deserialize or getValCurs? pretty much the same
        //QUESTION - How to mock HttpResponse here if getContentFromServer method is called inside getValCurs method and is static
        //MY ANSWER - Need to remake all static method to public in terms to call them from created BnmWebService object
        LocalDate cursDate = LocalDate.of(2017, 10, 10);

        ValCurs valCursObj = BnmWebService.getValCurs(cursDate);

        assertEquals(ValCurs.class, valCursObj.getClass());
    }

    @Test
    public void getValuteByCharCode() {
        /* TODO: Create test to verify that getValuteByCharCode returs Valute object with appropriate CharCode.
            HttpResponse to be mocked */
        LocalDate cursDate = LocalDate.of(2017, 10, 10);
        String charCode = "USD";

       Valute valuteObj = BnmWebService.getValuteByCharCode(cursDate, charCode);

       assertEquals(Valute.class, valuteObj.getClass());
       assertEquals(charCode, valuteObj.getCharCode());
    }

    @Test
    public void getMaxValute() {
        //TODO: Create test to verify that getMaxValute returs Valute object with max Value. HttpResponse to be mocked
        LocalDate cursStartDate = LocalDate.of(2000, 10, 10);
        LocalDate cursEndDate = LocalDate.of(2000, 10, 15);
        String charCode = "USD";
        double expectedMaxValue = 12.2668;

        Valute valuteObj = BnmWebService.getMaxValute(cursStartDate, cursEndDate, charCode);

        assertEquals(Valute.class, valuteObj.getClass());
        assertEquals(charCode, valuteObj.getCharCode());
        assertEquals(expectedMaxValue, valuteObj.getValue(), 0.00001);
    }
}