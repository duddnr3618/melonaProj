package com.fundguide.melona.board.like.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/like")
public class LikeController {
    @PostMapping("/uplike")
    public String uplike() {

        return null;
    }

}
