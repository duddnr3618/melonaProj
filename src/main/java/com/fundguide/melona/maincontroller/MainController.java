package com.fundguide.melona.maincontroller;

import com.fundguide.melona.member.service.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @GetMapping("/")
  public String index () {

    return "index";
  }




}
