package com.application.virgo.service.interfaces;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.model.Utente;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PdfGeneratorService {

    public void exportPDF(Utente idUtente, Long idContratto, HttpServletResponse response) throws ContrattoException, ContrattoUtenteException, IOException, ImmobileException;
}
