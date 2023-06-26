package com.application.virgo.service.implementation;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.ContrattoService;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.service.interfaces.ImmobileService;
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
    private final ImmobileService immobileService;
    private final ContrattoService contrattoService;

    /**
     * Permette di scaricare un pdf di riepilogo di un contratto
     * @param authUser id utente interessato
     * @param idContratto id contratto
     * @param response risposta http
     * @return id utente che ha richiesto il contratto
     * @throws ContrattoException se il contratto non esiste
     * @throws ContrattoUtenteException se l'assocazione contratto utente non esiste
     * @throws IOException se ho problemi con il pdf
     * @throws ImmobileException se l'immobile non esiste
     */
    @Override
    public Long exportPDF(Utente authUser, Long idContratto, HttpServletResponse response)
            throws ContrattoException, ContrattoUtenteException, IOException, ImmobileException {

            try (PDDocument document = new PDDocument()) {
                // prelevo informazioni contratto utente
                Optional<ContrattoUtente> tempContrattoUtente =
                        contrattoUtenteService.getContrattoByIdUtenteAndIdContratto(authUser, idContratto);

                Contratto contratto;

                if(tempContrattoUtente.isPresent()){
                    // get contratto utente from optional
                    ContrattoUtente contrattoUtente = tempContrattoUtente.get(); // accede ai dati degli utenti nel contratto
                    // prelevo info contratto dal database
                    contratto = contrattoService.getContrattoById(contrattoUtente.getIdContrattoUtente().getIdContratto()).get();

                    // prelevo informazioni immobile da database
                    Immobile immobile =
                            immobileService.getImmobileInfoForContratto(contratto.getImmobileInteressato().getIdImmobile()).get();
// =============================================================== CREO PDF ===================================================
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
                    contentStream.showText("Tra le seguenti parti:");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("VENDITORE:"+
                            contrattoUtente.getVenditore().getNome()+ " " +
                            contrattoUtente.getVenditore().getCognome() );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("ACQUIRENTE:"+
                            contrattoUtente.getAcquirente().getNome() + " " +
                            contrattoUtente.getAcquirente().getCognome());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Si stipula il presente contratto di acquisto immobiliare per");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText(" la compravendita del seguente immobile:");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("DESCRIZIONE DELL'IMMOBILE:"); contentStream.newLineAtOffset(0, -20);
                    contentStream.showText(immobile.getVia());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText(immobile.getDescrizione());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("TERMINI E CONDIZIONI:");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("    Prezzo di vendita: " + contratto.getPrezzo());
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("    Modalità di pagamento:");
                    contentStream.showText("        " );
                    contentStream.showText("        Scadenza: " );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("    Vincoli e diritti sull'immobile:" );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("    Stato dell'immobile:" );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("        L'immobile viene venduto senza garanzie espresse");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("o implicite sulla sua condizione.");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("    Passaggio di proprietà:");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("        Il passaggio di proprietà avverrà al momento del pagamento completo dell'importo");
                    contentStream.showText(" di vendita.");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Spese aggiuntive:");
                    contentStream.showText("Le spese di registrazione, notaio, tasse e qualsiasi altra spesa correlata alla vendita ");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText( "saranno a carico dell'acquirente/venditore secondo l'accordo delle parti.");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Risoluzione del contratto:" );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("In caso di inadempienza da parte di una delle parti, il contratto potrà  ");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText(" essere risolto secondo quanto previsto dalla legge applicabile." );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("    Legge applicabile e foro competente:" );contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("        Il presente contratto è regolato dalle");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText(" leggi dello Stato Italiano." );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("        Eventuali controversie saranno ");contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("risolte presso il Tribunale di ." );contentStream.newLineAtOffset(0, -20);
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Le parti dichiarano di aver letto e compreso il contenuto di questo " );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("contratto di acquisto immobiliare e accettano i termini e");
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText(" le condizioni in esso indicati." );
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.showText("Firmato in due copie originali in data " + FORMATTER.format(contratto.getDataStipulazione()));
                    contentStream.newLineAtOffset(0, -20);
                    contentStream.endText();
                    contentStream.close();

                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "attachment; filename=contratto_"+
                            FORMATTER.format(contratto.getDataStipulazione()).replace(" ","_")+".pdf");
                    document.save(response.getOutputStream());
// ===========================================================================================================================00
                    return contratto.getIdContratto();

                }


            }

            return idContratto;
        }

    }




