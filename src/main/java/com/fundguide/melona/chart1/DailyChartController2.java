package com.fundguide.melona.chart1;


import com.news.news.chart1.dto.ChatDto2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class DailyChartController2 {


    @GetMapping("/daily-chart2")
    public String getDailyChart(Model model) {
        String url = "https://financialmodelingprep.com/api/v3/historical-chart/1min/AAPL?from=2023-10-09&to=2023-07-09&apikey=e423e415ecc6e3cff2738f3b316f7abc";
        RestTemplate restTemplate = new RestTemplate();

        ChatDto2[] chatDtos2 = restTemplate.getForObject(url, ChatDto2[].class);
        model.addAttribute("data", Arrays.asList(chatDtos2));

        return "daily-chart2";
    }

//    @GetMapping("/daily-chart")
//    public String getDailyChart() throws Exception {
//        URL url = new URL("https://financialmodelingprep.com/api/v3/quote/AAPL?apikey=e423e415ecc6e3cff2738f3b316f7abc");
//        StringBuilder response = new StringBuilder();
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
//            for (String line; (line = reader.readLine()) != null; ) {
//                response.append(line);
//            }
//        }
//
//        return response.toString();
//    }


//    @GetMapping("/daily-chart2")
//    public ResponseEntity<List<ChatDto2>> getDailyChart2() {
//        String url = "https://financialmodelingprep.com/api/v3/historical-chart/1min/AAPL?from=2023-10-09&to=2023-07-09&apikey=e423e415ecc6e3cff2738f3b316f7abc";
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Get the response in the form of ChatDto2 array
//        ChatDto2[] chatDtos2 = restTemplate.getForObject(url, ChatDto2[].class);
//
//        return ResponseEntity.ok().body(Arrays.asList(chatDtos2));
//    }

}

//    @GetMapping("/daily-chart2")
//    public ResponseEntity<List<ChatDto2>> getDailyChart2() {
//        String url = "https://financialmodelingprep.com/api/v3/historical-chart/1min/AAPL?from=2023-10-09&to=2023-07-09&apikey=e423e415ecc6e3cff2738f3b316f7abc";
//        RestTemplate restTemplate = new RestTemplate();
//
//        // Get the response in the form of ChatDto2 array
//        ChatDto2[] chatDtos2 = restTemplate.getForObject(url, ChatDto2[].class);
//
//        return ResponseEntity.ok().body(Arrays.asList(chatDtos2));
//    }