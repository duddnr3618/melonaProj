package com.fundguide.melona.member.config;

import com.fundguide.melona.member.utils.MainSend;
import com.fundguide.melona.member.utils.UtilsPasswordEncoder;
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
}
