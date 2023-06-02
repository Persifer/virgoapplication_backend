package com.application.virgo.service.implementation;

import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
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
public class AuthServiceImpl {

    private final UtenteService utenteService;

    public Optional<Utente> getAuthUtente() throws UtenteException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return utenteService.getUtenteClassByEmail(auth.getName());
    }
}
