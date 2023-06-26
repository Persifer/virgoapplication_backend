package com.application.virgo.service.interfaces;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.PreventivoException;

public interface CalcoloPreventiviService {

    /**
     * Permette di calcolare il preventivo di ristrutturazione di un immobile tramite strategy pattern
     * @param idContratto id contratto di riferimento
     * @param selettoreAzienda parametro che seleziona l'azienda
     * @return il valore del preventivo
     * @throws ContrattoException se il contratto non esiste
     * @throws PreventivoException se ci sono errori nel calcolo
     */
    public Double calcolaPreventivoImmobile(Long idContratto, Integer selettoreAzienda) throws ContrattoException, PreventivoException;
}
