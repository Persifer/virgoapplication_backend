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

                Paragraph paragraph = new Paragraph("Contratto.", fontTitle);
                paragraph.setAlignment(Paragraph.ALIGN_CENTER);

                Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
                fontParagraph.setSize(12);

                Paragraph paragraph2 = new Paragraph(, fontParagraph);
                paragraph2.setAlignment(Paragraph.ALIGN_LEFT);

                document.add(paragraph);
                document.add(paragraph2);
                document.close();
            }
        }

    }


}
