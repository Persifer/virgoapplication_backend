package com.application.virgo.service.interfaces;

import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface AuthService {
    public Optional<Utente> getAuthUtente() throws UtenteException;
}
