package com.application.virgo.service.interfaces;


import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Immobile;

import java.util.Optional;

public interface ContrattoService {

    /**
     * Metodo che permette la creazione di un nuovo contratto a seguito di un un'offerta accettata
     * @param immobileInteressato l'entità immobile che è interessata nell'offerta
     * @param prezzoFinale prezzo finale proposto nell'offerta
     * @return il contratto creato tra due utenti
     * @throws ImmobileException se l'immobile non esiste
     */
    public Optional<Contratto> createNewContratto(Immobile immobileInteressato, Float prezzoFinale) throws ImmobileException;
}
