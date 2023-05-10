package com.application.virgo.configuration;

import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.implementation.SecuredUtenteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class VirgoSecurityConfiguration {

    //Global override di UserDetailsService


    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // Ignore HTTP authentication chain for these static files
        return web -> web.ignoring().requestMatchers("/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/registration").permitAll();

        http.rememberMe()
                .key("session")
                .userDetailsService(userDetailsService);
        // Enable login
        http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll();
        // Enable logout
        http.logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .permitAll();
        // Build chain
        return http.build();



    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UtenteJpaRepository utenteJpaRepository) {
        return new SecuredUtenteService(utenteJpaRepository);
    }

}
