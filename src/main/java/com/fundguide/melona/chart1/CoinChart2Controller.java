package com.fundguide.melona.chart1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class CoinChart2Controller {
    @GetMapping("/chart/coinchart/coinchart2")
    public String showStockInfo() {
        return "chart/coinchart/coinchart2";
    }
}
