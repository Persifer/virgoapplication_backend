package com.application.virgo.configuration;

import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.implementation.SecuredUtenteService;
import com.application.virgo.utilities.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Classe di configurazione di Spring Security
 */
@Configuration
@EnableWebSecurity
public class VirgoSecurityConfiguration{


    /**
     * Permette di configurare i filtri e le regole di sicurezza che lavoreranno all'interno dell'applicativo
     * @param http classe http per gestire gli end-point da mettere in sicurezza
     * @return La catena di filtri da utilizzare
     * @throws Exception se qualcosa va storto
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // disabili il cross site request forge per abilitare la comunicazione
                .csrf().disable()
                // permetto tutte le comunicazioni tranne quelle sotto /site/**
                .authorizeHttpRequests().requestMatchers("/site/**").hasRole("USER")
                .anyRequest().permitAll()
                .and()
                // abilito la form login
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/site/immobile/list/0/20")
                        .permitAll()
                )
                // abilito logout
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling().accessDeniedPage("/access-denied");

        // Build chain
        return http.build();



    }


}
