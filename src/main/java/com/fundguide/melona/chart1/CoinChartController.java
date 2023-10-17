package com.fundguide.melona.chart1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CoinChartController {
    @GetMapping("/chart/coinchart/coinchart")
    public String showStockInfo() {
        return "/chart/coinchart/coinchart";
    }
}
