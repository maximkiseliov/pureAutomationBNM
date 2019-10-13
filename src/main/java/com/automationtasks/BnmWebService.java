package com.automationtasks;

import com.automationtasks.Helpers.DatesHelper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class BnmWebService {
    private static final String BNM_URL = "https://www.bnm.md/en/official_exchange_rates?get_xml=1&date=";

    private static String getBnmUrl() {
        return BNM_URL;
    }

    public static String generateRequestLink(String date) {
        return getBnmUrl() + date;
    }

    public static InputStream getContentFromServer(String link) {
        CloseableHttpClient httpClient;
        HttpGet getRequest;
        HttpResponse response;
        InputStream httpContent;

        httpContent = null;

        try {
            httpClient = HttpClientBuilder.create().build();
            getRequest = new HttpGet(link);
            getRequest.addHeader("accept", "application/xml");
            response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200)
                throw new RuntimeException("Failed with HTTP error code : " + response.getStatusLine().getStatusCode());

            httpContent = response.getEntity().getContent();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return httpContent;
    }

    public static <T> T deserialize(InputStream content, Class<T> type){
        XStream xstream;
        T object = null;

        xstream = new XStream();

        try {
            xstream.processAnnotations(type);
            object = (T) xstream.fromXML(content);
        } catch (CannotResolveClassException e){
            System.out.println("Cannot Resolve Class");
        }

        return object;
    }

    //Task #4
    public static ValCurs getValCurs(LocalDate date) {
        String requestLink = generateRequestLink(DatesHelper.transformLocalDateToString(date));

        return deserialize(getContentFromServer(requestLink), ValCurs.class);
    }

    //Task #5
    public static Valute getValuteByCharCode(LocalDate date, String charCode) {
        Valute valute;

        String requestLink = generateRequestLink(DatesHelper.transformLocalDateToString(date));
        List<Valute> listOfValute = deserialize(getContentFromServer(requestLink), ValCurs.class).getValuteList();

        valute = listOfValute
                .stream()
                .filter(val -> charCode.equals(val.getCharCode()))
                .findAny()
                .orElse(null);

        return valute;
    }

    //Task #6
    // Solution #1
    public static Valute getMaxValute(LocalDate startDate, LocalDate endDate, String charCode) {
        List<String> listOfDates;
        XStream xstream;
        xstream = new XStream();
        xstream.processAnnotations(ValCurs.class);

//        String requestLink;
////        Valute valute, maxValute;
////        List<Valute> listOfValute;
////        List<Valute> listOfRequiredValute = new ArrayList<>();

        listOfDates = DatesHelper.listOfDatesBetweenStartAndEnd(startDate, endDate);

        return listOfDates.parallelStream()
                .map(BnmWebService::generateRequestLink)
                .map(BnmWebService::getContentFromServer)
                .map(xmlString -> (ValCurs) xstream.fromXML(xmlString))
                .map(ValCurs::getValuteList)
                .flatMap(List::stream)
                .filter(val -> charCode.equals(val.getCharCode()))
                .max(Comparator.comparing(Valute::getValue))
                .orElse(null);
// Solution #2
//        listOfDates.stream()
//                .map(BnmWebService::generateRequestLink)
//                .map(BnmWebService::getContentFromServer)
//                .map(xmlString -> (ValCurs) xstream.fromXML(xmlString))
//                .map(ValCurs::getValuteList)
//                .map(valutes -> valutes
//                        .stream()
//                        .filter(val -> charCode.equals(val.getCharCode())))
//                        .collect(Collectors.toList());

// Solution #3
//        for (String date : listOfDates) {
//            requestLink = generateRequestLink(date);
//
//            listOfValute = ((ValCurs) xstream.fromXML(getContentFromServer(requestLink))).getValuteList();
//            valute = listOfValute
//                    .stream()
//                    .filter(val -> charCode.equals(val.getCharCode()))
//                    .findAny()
//                    .orElse(null);
//
//            if (valute != null) {
//                listOfRequiredValute.add(valute);
//            }
//        }
//        maxValute = listOfRequiredValute
//                .stream()
//                .max(Comparator.comparing(Valute::getValue))
//                .get();
//
//        return maxValute;
    }
}
