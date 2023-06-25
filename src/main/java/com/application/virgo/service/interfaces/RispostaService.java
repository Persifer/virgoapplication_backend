package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.RispostaDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Risposta;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface RispostaService{

    /**
     * Permette di aggiungere una risposta ad una domanda
     * @param tempNewRisposta dati della nuova risposta
     * @param idDomanda domanda a cui rispondere
     * @param authUser utente autenticato
     * @param idImmobile immobile su cui è presente la domanda
     * @return La risposta salvata nel database
     * @throws ImmobileException se l'immobile non è presente
     * @throws UtenteException se l'utente non è presente
     */
    public Optional<Risposta> addNewRisposta(RispostaDTO tempNewRisposta, Long idDomanda, Utente authUser, Long idImmobile) throws ImmobileException, UtenteException, DomandaException;
}
