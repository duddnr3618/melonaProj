package com.fundguide.melona.member.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class StatisticWord {

    @Value("${koreaBank.key}")
    private String authKey;

    public String statisticWord(String search) {

        String jsonResponse = null;
        try {
            String encodedSearch = URLEncoder.encode(search, "UTF-8");
            String requestUrl = "http://ecos.bok.or.kr/api/StatisticWord/"+authKey+"/json/kr/1/10/"+encodedSearch;
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            jsonResponse = response.toString();
        } catch (Exception e) {

        }
        return jsonResponse;

    }
}
