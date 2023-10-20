package com.fundguide.melona.commonconfig;

import com.fundguide.melona.member.service.OAuth2UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurityConfig {

    private final OAuth2UserDetailService oAuth2UserDetailService;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity
                .authorizeRequests()
                .requestMatchers(("/css/**")).permitAll()
                .requestMatchers(("/js/**")).permitAll()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/leaderBoard/wrtieForm").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_SET_LEADER') or hasRole('ROLE_AUTO_LEADER')")
                .requestMatchers("/user/**").access("hasAnyRole('ROLE_USER') or hasRole('ROLE_ADMIN') or hasRole('ROLE_LEADER')")
                .requestMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN')")
                .requestMatchers("/","/logout").permitAll()
                .and()
                .headers((headers) -> headers.addHeaderWriter(
                        new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)
                ))

                .formLogin((formLogin) -> formLogin
                        .loginPage("/member/loginForm")
                        .loginProcessingUrl("/member/login")
                        .usernameParameter("memberEmail")
                        .passwordParameter("memberPassword")
                        .failureUrl("/fail")
                        .defaultSuccessUrl("/"))
                        .oauth2Login(oauth -> oauth.loginPage("/loginForm")
                        .defaultSuccessUrl("/oauth")
                        .failureUrl("/fail")
                        .userInfoEndpoint(userInfoEndpointConfig-> userInfoEndpointConfig.userService(oAuth2UserDetailService)))
                .logout((logout) -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true));
        return httpSecurity.build();
    }

  /*  @Bean
    PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}*/
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
