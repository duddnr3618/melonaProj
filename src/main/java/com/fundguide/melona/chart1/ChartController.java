package com.fundguide.melona.chart1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {
    @GetMapping("/chart/stockinformation/stockinfo1")
    public String showStockInfo() {
        return "/chart/stockinformation/stockinfo1";
    }
}
