package com.application.virgo.service.interfaces;

import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface AuthService {
    /**
     * Metodo che permette di prelevare dal contesto di Spring Security l'utente autenticato
     *
     * @return Un optional contenente l'utente autenticato
     * @throws UtenteException se l'utente non esiste
     */
    public Optional<Utente> getAuthUtente() throws UtenteException;
}
