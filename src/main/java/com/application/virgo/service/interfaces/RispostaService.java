package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.RispostaDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Risposta;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface RispostaService{

    public Optional<Risposta> addNewRisposta(RispostaDTO tempNewRisposta, Long idDomanda, Utente authUser, Long idImmobile) throws ImmobileException, UtenteException;
}
