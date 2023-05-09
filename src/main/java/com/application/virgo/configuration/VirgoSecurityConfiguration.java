package com.application.virgo.configuration;

import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.implementation.SecuredUtenteService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class VirgoSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Global override di UserDetailsService
    @Bean
    public UserDetailsService userDetailsService(UtenteJpaRepository utenteJpaRepository) {
        return new SecuredUtenteService(utenteJpaRepository);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                //.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/registration/*").permitAll()
             /*   // Enable custom login
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/", true)
                    .permitAll()
                .and()
                // Enable logout
                .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and() */
                //.headers(headers -> headers.frameOptions().sameOrigin())
//                .and()
//                .httpBasic(withDefaults())
                .and()
                .build();
    }



}
