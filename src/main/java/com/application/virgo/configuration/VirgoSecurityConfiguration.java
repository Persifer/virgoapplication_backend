package com.application.virgo.configuration;

import com.application.virgo.service.SecuredUtenteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class VirgoSecurityConfiguration {

    private final SecuredUtenteService securedUtenteService;

    public VirgoSecurityConfiguration(SecuredUtenteService myUserDetailsService) {
        this.securedUtenteService = myUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeRequests(auth -> auth
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/registration").permitAll()
                        .anyRequest().authenticated()
                )
                .userDetailsService(securedUtenteService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(withDefaults())
                .build();
    }



}
