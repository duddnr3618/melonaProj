package com.fundguide.melona.board.leaderboard.controller;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUsernameNotFoundException() {
        return "redirect:/member/loginForm";
    }
}