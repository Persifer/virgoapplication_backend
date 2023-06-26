package com.application.virgo.service.implementation;

import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UtenteService utenteService;

    /**
     * Metodo che permette di prelevare dal contesto di Spring Security l'utente autenticato
     *
     * @return Un optional contenente l'utente autenticato
     * @throws UtenteException se l'utente non esiste
     */
    public Optional<Utente> getAuthUtente() throws UtenteException{
        // prelevo l'utente autenticato dal contesto spring
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return utenteService.getUtenteClassByEmail(auth.getName());
    }
}
