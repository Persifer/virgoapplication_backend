package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.LoginUtenteDTO;
import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface UtenteService {

    Optional<Utente> getUtenteByEmailAndPassword(String username, String password);

    Optional<UtenteDTO> getUtenteById(Long idUtenteToFound) throws UtenteException;

    Optional<Utente> updateUtenteInfoById(Long idUtente, UtenteDTO newUtente) throws UtenteException;

    Optional<Utente> registrationHandler(Utente newUtente) throws UtenteException;
    Optional<Utente> loginHandler(LoginUtenteDTO tempUtente) throws UtenteException;
    boolean login(String email, String password) throws UtenteException;
    Optional<Utente> getUtenteClassById(Long idProprietario) throws UtenteException;
}
