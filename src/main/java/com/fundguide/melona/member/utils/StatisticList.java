package com.fundguide.melona.member.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StatisticList {

    @Value("${koreaBank.key}")
    private String authKey;

    public String statistics() {


        String jsonResponse = null;
        try {
            String requestUrl = "http://ecos.bok.or.kr/api/KeyStatisticList/" + authKey + "/json/kr/1/100";
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
