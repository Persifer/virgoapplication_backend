package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.LoginUtenteDTO;
import com.application.virgo.DTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface UtenteService {

    public Optional<Utente> getUtenteByEmailAndPassword(String username, String password);

    public Optional<UtenteDTO> getUtenteById(Long idUtenteToFound) throws UtenteException;

    public Optional<Utente> updateUtenteInfoById(Long idUtente, UtenteDTO newUtente) throws UtenteException;

    public Optional<Utente> registrationHandler(Utente newUtente) throws UtenteException;
    public Optional<Utente> loginHandler(LoginUtenteDTO tempUtente) throws UtenteException;

}
