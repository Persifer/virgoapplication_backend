package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.exception.DomandaException;
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
    public Optional<Domanda> addNewDomanda(DomandaDTO tempDomandaDTO, Utente authUser)
            throws UtenteException;

    /**
     * Permette di rispondere ad una domanda da parte del proprietario
     * @param risposta rispsota da associare ad una domanda
     * @param idDomanda id domanda selezionata
     * @return la domanda a cui abbiamo aggiunto al risposta
     * @throws DomandaException se non è possibile reperire la domanda
     * @throws UtenteException se non è possibile reperire l'utente autenticato
     * @throws RispostaException se non è possibile reperire la risposta da pubblicare
     */
    public Optional<Domanda> replyToDomanda(Risposta risposta, Long idDomanda) throws DomandaException, UtenteException, RispostaException;
}
