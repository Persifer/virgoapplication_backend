package com.application.virgo.service.implementation;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.ContrattoService;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.service.interfaces.PdfGeneratorService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private final ContrattoUtenteService contrattoUtenteService;
    private final ContrattoService contrattoService;


    @Override
    public void exportPDF(Utente authUser, Long idContratto, HttpServletResponse response)
            throws ContrattoException, ContrattoUtenteException, IOException {

        Optional<ContrattoUtente> tempContrattoUtente =
                contrattoUtenteService.getContrattoByIdUtenteAndIdContratto(authUser, idContratto);

        Optional<Contratto> tempContratto;

        if(tempContrattoUtente.isPresent()){

            ContrattoUtente contrattoUtente = tempContrattoUtente.get(); // accede ai dati degli utenti nel contratto

            tempContratto = contrattoService.getContrattoById(contrattoUtente.getIdContrattoUtente().getIdContratto());

            if(tempContratto.isPresent()){
                Contratto contratto = tempContratto.get(); // accede ai dati del contratto vero e proprio
                Document document = new Document(PageSize.A4);

                PdfWriter.getInstance(document, response.getOutputStream());

                document.open();
                Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
                fontTitle.setSize(18);

                String titolo = "Contratto digitale riferito a: " + contratto.getIdContratto();

                Paragraph paragraph = new Paragraph(titolo, fontTitle);
                paragraph.setAlignment(Paragraph.ALIGN_CENTER);

                Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
                fontParagraph.setSize(12);

                String stipulazione = "Stipulato in data: "+ contratto.getDataStipulazione();

                Paragraph paragraph2 = new Paragraph(stipulazione, fontParagraph);
                paragraph2.setAlignment(Paragraph.ALIGN_RIGHT);


                String nomiParti = "Nome acquirente: " + contrattoUtente.getAcquirente().getNome() +"\n" +
                                    "Cognome acquirente: " + contrattoUtente.getAcquirente().getCognome() +"\n\n" +
                                    "Nome venditore: " + contrattoUtente.getVenditore().getNome() +"\n" +
                                    "Cognome acquirente: " + contrattoUtente.getVenditore().getCognome() +"\n"  +
                                    "Prezzo concordato: " + contratto.getPrezzo() +"\n"  ;

                Paragraph paragraph3 = new Paragraph(nomiParti, fontParagraph);
                paragraph3.setAlignment(Paragraph.ALIGN_LEFT);

                String immobile = "Il contratto riguarda l'immobile in via" + contratto.getImmobileInteressato().getVia() +
                        "Nella città di: " + contratto.getImmobileInteressato().getCitta()
                        ;

                Paragraph paragraph4 = new Paragraph(immobile, fontParagraph);
                paragraph4.setAlignment(Paragraph.ALIGN_LEFT);

                document.add(paragraph);
                document.add(paragraph2);
                document.add(paragraph3);
                document.add(paragraph4);
                document.close();
            }
        }

    }


}
