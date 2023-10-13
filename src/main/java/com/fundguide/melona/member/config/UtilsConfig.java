package com.fundguide.melona.member.config;

import com.fundguide.melona.member.utils.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
@RequiredArgsConstructor
public class UtilsConfig {


    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Bean
    public PasswordEncoder utilsPasswordEncoder() {
        return new UtilsPasswordEncoder();
    }

    @Bean
    public MainSend mainSend() {
        return new MainSend(mailSender, templateEngine);
    }

    @Bean
    public StatisticList koreaBank(){
        return new StatisticList();
    }
    @Bean
    public ExchangeRate exchangeRate(){
        return new ExchangeRate();
    }

    @Bean
    public StatisticWord statisticWord(){
        return new StatisticWord();
    }
}
