package com.fundguide.melona.chart1;


//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//
//@Component
//public class DailyChart {
//
//    @Value("${financial-key}")
//    private String apiKey;
//
//    public void fetchDailyData() throws Exception {
//        URL url = new URL("https://portal.financialmodelingprep.com/https://financialmodelingprep.com/api/v3/historical-price-full/AAPL?apikey=" + apiKey);
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
//            for (String line; (line = reader.readLine()) != null; ) {
//                System.out.println(line);
//            }
//        }
//    }
//
//}
//
//
//







//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.URL;
//
//public class DailyChart {
//    public static void main(String[] args) throws Exception {
//
//        URL url = new URL("https://portal.financialmodelingprep.com/https://financialmodelingprep.com/api/v3/historical-price-full/AAPL?apikey=e423e415ecc6e3cff2738f3b316f7abc'");
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
//            for (String line; (line = reader.readLine()) != null; ) {
//                System.out.println(line);
//            }
//        }
//    }
//}