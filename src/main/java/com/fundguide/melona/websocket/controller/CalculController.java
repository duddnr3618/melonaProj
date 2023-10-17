package com.fundguide.melona.websocket.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/calculator/calculmain")
@RequiredArgsConstructor
public class CalculController {

    @GetMapping
    public String calcul(Model model){
        return "/calculator/calculmain";
    }
}
