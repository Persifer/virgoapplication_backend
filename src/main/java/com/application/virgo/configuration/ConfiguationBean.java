package com.application.virgo.configuration;

import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.implementation.SecuredUtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Classe che permette la configurazione dei bean utili a Spring security per il suo funzionamento come
 * il password encoder, lo user details service per l'autenticazione e l'authentication manager per gestire
 * l'autenticazione e
 * */
@Configuration
public class ConfiguationBean {

    @Autowired
    private SecuredUtenteService securedUtenteService;

    /**
     * Permette di settare la classe che cripta la password dell'utente
     * @return l'istanza della classe che cripta la password
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Permette di configurare lo user details service
     * @param utenteJpaRepository la repository da utilizzare per reperire i dati dell'utente
     * @return L'istanza configurata dello UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(UtenteJpaRepository utenteJpaRepository) {
        return new SecuredUtenteService(utenteJpaRepository);
    }

    /**
     * Permette di configurare il manager dell'autentication
     * @param authConfigration configurazione dell'autenticazione
     * @return il manager che si occupa dell'autenticazione
     * @throws Exception se non riesce e aconfigurare
     */
    @Bean
    public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration authConfigration) throws Exception{
        return authConfigration.getAuthenticationManager();
    }

    /**
     * Configura la classe che si comporta da fornitore dell'autenticazione
     * @return l'AuthenticationProvider configurato
     */
    @Bean
    public AuthenticationProvider getAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(securedUtenteService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

}
