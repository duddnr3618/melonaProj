package com.fundguide.melona.maincontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

  @GetMapping("/")
  @ResponseBody
  public String index () {

    return "컨트롤러 작동 확인";
  }

}
