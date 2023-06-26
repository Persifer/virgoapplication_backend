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

import static com.application.virgo.utilities.Constants.FORMATTER;

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

            Contratto contratto;

            if(tempContrattoUtente.isPresent()){

                ContrattoUtente contrattoUtente = tempContrattoUtente.get(); // accede ai dati degli utenti nel contratto

                contratto = contrattoService.getContrattoById(contrattoUtente.getIdContrattoUtente().getIdContratto()).get();

                PDPage page = new PDPage(PDRectangle.A4);
                document.addPage(page);

                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Contratto di Acquisto Immobiliare");
                contentStream.setFont(PDType1Font.COURIER, 12);
                contentStream.newLineAtOffset(0, -20);
                // modificare pdf con contentStream.showText() e contentStream.newLine()
                /*contentStream.showText("Tra le seguenti parti:) +
                        contentStream.newLine()
                        contentStream.showText("VENDITORE:" +
                                        contrattoUtente.getVenditore().getNome() +
                                        contrattoUtente.getVenditore().getCognome() +
                                        contentStream.newLine()
                                contentStream.showText("ACQUIRENTE:") +
                                        contrattoUtente.getAcquirente().getNome() +
                                        contrattoUtente.getAcquirente().getCognome() +
                                        contentStream.newLine()
                                contentStream.showText("Si stipula il presente contratto di acquisto immobiliare per la compravendita del seguente immobile:" +
                                                contentStream.newLine()
                                        contentStream.showText("DESCRIZIONE DELL'IMMOBILE:" +
                                                        contratto.getImmobileInteressato().getVia() +
                                                        contratto.getImmobileInteressato().getDescrizione()+
                                                        contentStream.newLine()
                                                contentStream.showText("TERMINI E CONDIZIONI:" +
                                                                contentStream.newLine()
                                                        contentStream.showText("    Prezzo di vendita: " + contratto.getPrezzo()+
                                                                        contentStream.newLine()
                                                                contentStream.showText("    Modalità di pagamento:" +
                                                                        contentStream.showText("        " +
                                                                                contentStream.showText("        Scadenza: " +
                                                                                                contentStream.newLine()
                                                                                        contentStream.showText("    Vincoli e diritti sull'immobile:" +
                                                                                                contentStream.newLine();
                contentStream.newLine();
                contentStream.newLine();
                "    Stato dell'immobile:" +
                        "        L'immobile viene venduto \"così com'è\", senza garanzie espresse o implicite sulla sua condizione." +
                        contentStream.newLine()
                "    Passaggio di proprietà:" +
                        "        Il passaggio di proprietà avverrà al momento del pagamento completo dell'importo di vendita." +
                        contentStream.newLine()
                "    Spese aggiuntive:" +
                        "        Le spese di registrazione, notaio, tasse e qualsiasi altra spesa correlata alla vendita saranno a carico dell'acquirente/venditore secondo l'accordo delle parti." +
                        contentStream.newLine()
                "    Risoluzione del contratto:" +
                        "        In caso di inadempienza da parte di una delle parti, il contratto potrà essere risolto secondo quanto previsto dalla legge applicabile." +
                        contentStream.newLine()
                "    Legge applicabile e foro competente:" +
                        "        Il presente contratto è regolato dalle leggi dello Stato [specificare lo Stato]." +
                        "        Eventuali controversie saranno risolte presso il Tribunale di [specificare il tribunale competente]." +
                        contentStream.newLine()
                "Le parti dichiarano di aver letto e compreso il contenuto di questo contratto di acquisto immobiliare e accettano i termini e le condizioni in esso indicati." +
                        contentStream.newLine()
                "Firmato in due copie originali in data ." + FORMATTER.format(contratto.getDataStipulazione()); */

                contentStream.showText("text");
                contentStream.newLineAtOffset(0, -20);
                contentStream.endText();
                contentStream.close();

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=contratto_"+
                        FORMATTER.format(contratto.getDataStipulazione()).replace(" ","_")+".pdf");
                document.save(response.getOutputStream());

            }


        }


    }

}



