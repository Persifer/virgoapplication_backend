package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Offerta;

import java.util.Optional;

public interface OffertaService {

    /**
     * Permette di creare una nuova offerta
     * @param datiOfferta dati dell'offerta da creare
     * @return l'offerta creata
     * @throws OffertaException se non è stato possibile creare l'offerta
     * @throws ImmobileException se non è stato possibile trovare l'immobile
     */
    public Optional<Offerta> createNewOfferta(InsertOffertaDTO datiOfferta)
            throws OffertaException, ImmobileException;

    /**
     * Permette di avere i dati di un offerta
     * @param idOfferta id offerta di cui si vogliono avere i dati
     * @return L'offerta voluta
     * @throws OffertaException se l'offerta non esiste
     */
    public Optional<Offerta> getOffertaDetails(Long idOfferta) throws OffertaException;
}
