package com.fundguide.melona.websocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calculator/calender")
@RequiredArgsConstructor
public class CalenderController {

    @GetMapping
    public String calcul(Model model){
        return "/calculator/calender";
    }
}
