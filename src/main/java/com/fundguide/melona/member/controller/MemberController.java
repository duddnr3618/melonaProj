package com.fundguide.melona.member.controller;

import com.fundguide.melona.member.dto.MemberDto;
import com.fundguide.melona.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;


    @GetMapping("/loginForm")
    public String loginForm(){
        return "member/loginForm";
    }


    @GetMapping("/joinForm")
    public String joinForm(){

        return "member/joinFrom";
    }
    @PostMapping("/join")
    public String memberSave(@ModelAttribute MemberDto memberDto) {
        memberService.memberSave(memberDto);
        return "member/success";
    }
    @GetMapping("/success")
    public String success(){
        return "member/success";
    }
    @GetMapping("/fail")
    public String fail(){
        return "member/fail";
    }

}
