package com.fundguide.melona.member.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundguide.melona.member.dto.ExchangeDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class ExchangeRate  {



        public ArrayList<ExchangeDto> requestApi() {

            String authKey = "W3m9j0w1gMB5Y8GNtk5nvnK4VQpCeAuG";
            String searchDate = "20230927";
            String data = "AP01";
            String requestUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + authKey + "&searchdate=" + searchDate + "&data=" + data;

            ArrayList<ExchangeDto> exchangeDtos = null;
            try {
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

                String jsonResponse = response.toString();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                exchangeDtos = new ArrayList<>();
                if (jsonNode.isArray()) {
                    for (JsonNode node : jsonNode) {
                        String name = node.get("cur_nm").asText();
                        String ttb = node.get("ttb").asText();
                        String tts = node.get("tts").asText();



                        ExchangeDto exchangeDto = new ExchangeDto();

                        exchangeDto.setCurrencyName(name);
                        exchangeDto.setTts(tts);
                        exchangeDto.setTtb(ttb);
                        exchangeDtos.add(exchangeDto);
                    }
                }
            } catch (Exception e) {

            }
            return exchangeDtos;
        }



}
