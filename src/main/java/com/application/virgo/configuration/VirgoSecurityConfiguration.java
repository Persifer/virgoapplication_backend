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

@Configuration
@EnableWebSecurity
public class VirgoSecurityConfiguration{

    //Global override di UserDetailsService
  //  @Autowired
    //@Qualifier("securedUser")
   // private UserDetailsService securedUtenteService;



//   @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // Ignore HTTP authentication chain for these static files
//        return web -> web.ignoring().requestMatchers("/favicon.ico","/bower_components/**", "/dist/**", "/plugins/**");
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       /* http.authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/registration").permitAll()
                .requestMatchers(HttpMethod.POST, "/registration").permitAll()
                .requestMatchers("/site/**").hasRole(Constants.USER_ROLE);
                //.anyRequest().authenticated();
        http
            .authorizeHttpRequests((authorize) -> authorize
                            .requestMatchers( "/registration").permitAll()
                            .requestMatchers( "/registration").permitAll()
                    //.anyRequest().authenticated())
            ).securityContext((securityContext) -> securityContext
                    .requireExplicitSave(false));

        // Enable login
        /*http.formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/login/success", true)
                //.failureForwardUrl("/fail")
                .permitAll();

        // Enable logout
        http.logout()
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/login")
                .permitAll();
        http.rememberMe()
                .key("session")
                .userDetailsService(userDetailsService); */

//        http
//                .csrf().disable()
//                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers(HttpMethod.GET, "/registration").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/registration").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(
//                        form -> form
//                                .loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .defaultSuccessUrl("/users")
//                                .permitAll()
//                )
//                .headers(header -> header.frameOptions().sameOrigin())
//                .logout((logout) -> logout.permitAll());
        http
                .csrf().disable()
                .authorizeHttpRequests().requestMatchers("/site/**").hasRole("USER")
                .anyRequest().permitAll()
                .and()
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/site/vai")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll)
                .exceptionHandling().accessDeniedPage("/access-denied");

        // Build chain
        return http.build();



    }


}
