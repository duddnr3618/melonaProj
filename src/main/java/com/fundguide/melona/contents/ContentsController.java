package com.fundguide.melona.contents;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentsController {

    @GetMapping("/contents/board1")
    public String contents1() {
        return "contents/content_1";
    }

    @GetMapping("/contents/board2")
    public String contents2() {
        return "contents/content_2";
    }

    @GetMapping("/contents/board3")
    public String contents3() {
        return "contents/content_3";
    }
}
