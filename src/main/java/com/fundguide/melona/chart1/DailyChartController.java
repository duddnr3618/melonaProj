package com.fundguide.melona.chart1;


import com.news.news.chart1.dto.ChatDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Controller

public class DailyChartController {

    @GetMapping("/daily-chart")
    public String getDailyChart(Model model) {
        String url = "https://financialmodelingprep.com/api/v3/quote/AAPL?apikey=e423e415ecc6e3cff2738f3b316f7abc";
        RestTemplate restTemplate = new RestTemplate();

        ChatDto[] chatDtos = restTemplate.getForObject(url, ChatDto[].class);

        model.addAttribute("chatDtos", Arrays.asList(chatDtos));

        return "daily-chart"; // Thymeleaf 파일 이름 (chart.html이라고 가정)
    }
}



//
//@RestController
//public class DailyChartController {
//
////    @GetMapping("/daily-chart")
////    public String getDailyChart() throws Exception {
////        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/AAPL?apikey=e423e415ecc6e3cff2738f3b316f7abc");
////        StringBuilder response = new StringBuilder();
////
////        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
////            for (String line; (line = reader.readLine()) != null; ) {
////                response.append(line);
////            }
////        }
////
////        return response.toString();
////    }
//
//
//    @GetMapping("/daily-chart")
//    public ResponseEntity<List<ChatDto>> getDailyChart() {
//        String url = "https://financialmodelingprep.com/api/v3/quote/AAPL?apikey=e423e415ecc6e3cff2738f3b316f7abc";
//        RestTemplate restTemplate = new RestTemplate();
//
//        ChatDto[] chatDtos = restTemplate.getForObject(url, ChatDto[].class);
//
//        return ResponseEntity.ok().body(Arrays.asList(chatDtos));
//    }
//
//}