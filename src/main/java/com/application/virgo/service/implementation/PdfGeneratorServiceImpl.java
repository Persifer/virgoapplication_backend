package com.application.virgo.service.implementation;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.ContrattoService;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.service.interfaces.PdfGeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        try (PDDocument document = new PDDocument()) {

            Optional<ContrattoUtente> tempContrattoUtente =
                    contrattoUtenteService.getContrattoByIdUtenteAndIdContratto(authUser, idContratto);

            Optional<Contratto> tempContratto;

            if(tempContrattoUtente.isPresent()){

                ContrattoUtente contrattoUtente = tempContrattoUtente.get(); // accede ai dati degli utenti nel contratto

                tempContratto = contrattoService.getContrattoById(contrattoUtente.getIdContrattoUtente().getIdContratto());

                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("CONTRATTO DI VENDITA");
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Acquirente: " + contrattoUtente.getAcquirente().getNome()+ " "
                        + contrattoUtente.getAcquirente().getCognome());
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Prezzo: " + tempContratto.get().getPrezzo() + " € / EUR");
                contentStream.endText();
                contentStream.close();

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=contratto.pdf");
                document.save(response.getOutputStream());

            }


        }


    }

}



