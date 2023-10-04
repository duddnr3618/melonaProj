package com.fundguide.melona.member.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fundguide.melona.member.dto.ExchangeDto;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
public class ExchangeRate {

    public String requestProcess(String searchDate){
        String authKey = "W3m9j0w1gMB5Y8GNtk5nvnK4VQpCeAuG";
        String data = "AP01";
        String requestUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=" + authKey
                + "&searchdate=" + searchDate + "&data=" + data;

        String jsonResponse=null;
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
            jsonResponse = response.toString();
            return jsonResponse;
        } catch (Exception e) {

        }
        return jsonResponse;

    }

    public ArrayList<ExchangeDto> requestApi() {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalTime elevenAM = LocalTime.of(11, 0);
        String result;
        int sum =0;
        if (currentTime.isBefore(elevenAM)) {
            currentDate = LocalDate.now().minusDays(1);
        }
        do {
            switch (currentDate.getDayOfWeek()) {
                case MONDAY:
                    if (currentTime.isBefore(elevenAM)) {
                        currentDate = currentDate.minusDays(3);
                    }
                    break;
                case SATURDAY:
                    currentDate = currentDate.minusDays(1);
                    break;
                case SUNDAY:
                    currentDate = currentDate.minusDays(2);
                    break;
                default:
                    break;
            }
            result = requestProcess(currentDate.toString());
            sum++;
            currentDate = currentDate.minusDays(sum);
        } while (result!=null && result.length()<10);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(result);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ArrayList<ExchangeDto> exchangeDtos = new ArrayList<>();
        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                if(node.get("cur_unit").asText().equals("KRW")) continue;
                ExchangeDto exchangeDto = ExchangeDto.builder()
                        //        .result(node.get("result").asInt())
                        .currencyUnit(node.get("cur_unit").asText())
                        .currencyName(node.get("cur_nm").asText())
                        .ttb(node.get("ttb").asText())
                        .tts(node.get("tts").asText())
                        .dealBasisRate(node.get("deal_bas_r").asText())
                        .yyefeer(node.get("yy_efee_r").asText())
                        .bkpr(node.get("bkpr").asText())
                        .tenddefeer(node.get("ten_dd_efee_r").asText())
                        .kftcDeal(node.get("kftc_deal_bas_r").asText())
                        .kftcBkpr(node.get("kftc_bkpr").asText()).build();

                exchangeDtos.add(exchangeDto);
            }
        }
        for (ExchangeDto exchangeDto : exchangeDtos) {
            System.out.println("exchangeDto.getCurrencyName() = " + exchangeDto.getCurrencyName());
            System.out.println("exchangeDto.getCurrencyName() = " + exchangeDto.getTts());
        }
        return exchangeDtos;
}



   /* public ArrayList<ExchangeDto> requestApi2() {

        String authKey = "W3m9j0w1gMB5Y8GNtk5nvnK4VQpCeAuG";
        String searchDate = LocalDate.now().toString();    // 한국수출입은행  비영업일이나 영업일 11시 이전의 데이터 요청시 null
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
                    ExchangeDto exchangeDto = ExchangeDto.builder()
                    //        .result(node.get("result").asInt())
                            .currencyUnit(node.get("cur_unit").asText())
                            .currencyName(node.get("cur_nm").asText())
                            .ttb(node.get("ttb").asText())
                            .tts(node.get("tts").asText())
                            .dealBasisRate(node.get("deal_bas_r").asText())
                            .yyefeer(node.get("yy_efee_r").asText())
                            .bkpr(node.get("bkpr").asText())
                            .tenddefeer(node.get("ten_dd_efee_r").asText())
                            .kftcDeal(node.get("kftc_deal_bas_r").asText())
                            .kftcBkpr(node.get("kftc_bkpr").asText()).build();

                    exchangeDtos.add(exchangeDto);
                }
            }
        } catch (Exception e) {

        }
        return exchangeDtos;
    }*/


}
