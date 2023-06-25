package com.application.virgo.service.implementation;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.service.interfaces.PdfGeneratorService;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Document;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private final ContrattoUtenteService contrattoUtenteService;


    /*@Override
    public void exportPDF(Utente authUser, Long idContratto) throws ContrattoException, ContrattoUtenteException {
        Optional<ContrattoUtente> contrattoUtente = contrattoUtenteService.getContrattoByIdUtenteAndIdContratto(authUser, idContratto);
        if(contrattoUtente.isPresent()){
            ContrattoUtente contratto = contrattoUtente.get();
        }
    }*/

    @Override
    public void exportPDF(Utente authUser, Long idContratto, HttpServletResponse response)
            throws ContrattoException, ContrattoUtenteException, FileNotFoundException {

        Optional<ContrattoUtente> contrattoUtente = contrattoUtenteService.getContrattoByIdUtenteAndIdContratto(authUser, idContratto);
        if(contrattoUtente.isPresent()) {


            ContrattoUtente contratto = contrattoUtente.get();
            Document documento = new Document();

            PdfWriter.getInstance(documento, new FileOutputStream("C:\\progetto_ing_sw\\files\\pdf"));

            documento.open();
            documento.add(new Paragraph("Dettagli Contratto:"));
            documento.add(new Paragraph("Contratto id: " + contratto.getContrattoInteressato().getIdContratto()));

            documento.add(new Paragraph("Venditore: " +
                    contratto.getVenditore().getCognome() + " " + contratto.getVenditore().getNome()));


            documento.add(new Paragraph("Acquirente: " +
                    contratto.getAcquirente().getCognome() + " " + contratto.getAcquirente().getNome()));

            documento.add(new Paragraph("Prezzo finale immobile: " + contratto.getContrattoInteressato().getPrezzo()));

            documento.add(new Paragraph("Vogliamo ricordare che il contratto è vincolante per entrambe le parti"));
            documento.close();

            System.out.println("PDF exported successfully.");
        }
    }

}
