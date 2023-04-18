package com.application.virgo.service.interfaces;

import com.application.virgo.controller.DTO.LoginUtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface UtenteService {

    public Optional<Utente> getUtenteByEmailAndPassword(String username, String password);

    public Optional<Utente> updateUtenteInfoById(Long idUtente, Utente newUtente);

    public Optional<Utente> registrationHandler(Utente newUtente) throws UtenteException;
    public Optional<Utente> loginHandler(LoginUtenteDTO tempUtente) throws UtenteException;

}
