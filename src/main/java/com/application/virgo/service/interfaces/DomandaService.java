package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.RispostaException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Risposta;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface DomandaService {

    /**
     * Permette di pubblicare una domanda sotto un immobile
     * @param tempDomandaDTO dati relativi alla domanda
     * @param authUser utente autenticato
     * @return La domanda pubblicata
     * @throws UtenteException se l'utente autenticato non esiste
     */
    public Optional<Domanda> addNewDomanda(DomandaDTO tempDomandaDTO, Utente authUser, Long idImmobile)
            throws UtenteException, ImmobileException;

    /**
     * Permette di disabiliatare una domanda da parte dell'utente proprietario dell'immobile
     * @param auhtUser utente autenticato
     * @param idDomanda domanda da disabilitare
     * @return ritorna la domanda disabitata
     * @throws DomandaException se la domanda non esiste
     */
    public Optional<Domanda> disabilitaDomanda(Utente auhtUser, Long idDomanda) throws DomandaException;

    /**
     * Permette di prelevare i dati di una domanda dal database
     * @param idDomanda id domanda da prelevare
     * @return la domanda richiesta
     * @throws DomandaException se la domanda non esiste
     */
    public Optional<Domanda> getDomandainternalInformationById(Long idDomanda) throws DomandaException;
}
