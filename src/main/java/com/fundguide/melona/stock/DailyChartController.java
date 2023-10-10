package com.fundguide.melona.stock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


@RestController
public class DailyChartController {

    @GetMapping("/daily-chart")
    public String getDailyChart() throws Exception {
        URL url = new URL("https://portal.financialmodelingprep.com/https://financialmodelingprep.com/api/v3/historical-price-full/AAPL?apikey=e423e415ecc6e3cff2738f3b316f7abc'");
        StringBuilder response = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null; ) {
                response.append(line);
            }
        }

        return response.toString();
    }
}