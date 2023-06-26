package com.application.virgo.service.interfaces;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.model.Utente;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PdfGeneratorService {

    /**
     * Permette di scaricare un pdf di riepilogo di un contratto
     * @param idUtente id utente interessato
     * @param idContratto id contratto
     * @param response risposta http
     * @return id utente che ha richiesto il contratto
     * @throws ContrattoException se il contratto non esiste
     * @throws ContrattoUtenteException se l'assocazione contratto utente non esiste
     * @throws IOException se ho problemi con il pdf
     * @throws ImmobileException se l'immobile non esiste
     */
    public Long exportPDF(Utente idUtente, Long idContratto, HttpServletResponse response) throws ContrattoException, ContrattoUtenteException, IOException, ImmobileException;
}
