package com.application.virgo.service.interfaces;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.model.Utente;

public interface PdfGeneratorService {

    public void exportPDF(Utente idUtente, Long idContratto) throws ContrattoException, ContrattoUtenteException;
}
