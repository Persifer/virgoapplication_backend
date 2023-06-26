package com.application.virgo.service.implementation;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.PreventivoException;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Immobile;
import com.application.virgo.preventivi.ContextPreventivi;
import com.application.virgo.preventivi.implementation.AlgoritmoAziendaMPT;
import com.application.virgo.preventivi.implementation.AlgoritmoAziendaXYZ;
import com.application.virgo.service.interfaces.CalcoloPreventiviService;
import com.application.virgo.service.interfaces.ContrattoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CalcoloPreventiciServiceImpl implements CalcoloPreventiviService {
    
    private final ContrattoService contrattoService;

    /**
     * Permette di calcolare il preventivo di ristrutturazione di un immobile tramite strategy pattern
     * @param idContratto id contratto di riferimento
     * @param selettoreAzienda parametro che seleziona l'azienda
     * @return il valore del preventivo
     * @throws ContrattoException se il contratto non esiste
     * @throws PreventivoException se ci sono errori nel calcolo
     */
    @Override
    public Double calcolaPreventivoImmobile(Long idContratto, Integer selettoreAzienda)
            throws ContrattoException, PreventivoException, NumberFormatException {

        // prelevo dettagli contratto utente
        Optional<Contratto> tempContratto = contrattoService.getContrattoById(idContratto);

        if(tempContratto.isPresent()){
            // prelevo i dati dall'optional
            Contratto contratto = tempContratto.get();
            // prelevo l'immobile interessato
            Immobile immobileContratto = contratto.getImmobileInteressato();

            //creo il contesto dello strategy
            ContextPreventivi contextPreventivi = new ContextPreventivi();

            switch (selettoreAzienda){
                case 1:
                    // lo istanzio con la prima tipologia
                    contextPreventivi.setAlgoritmo(new AlgoritmoAziendaXYZ());
                    break;
                case 2:
                    // lo istanzio con la seconda tipologia
                    contextPreventivi.setAlgoritmo(new AlgoritmoAziendaMPT());
                    break;
                default:

                    throw new PreventivoException("L'azienda selezionata non esiste");
            }

            // ritorno l valrore calcolato
            return contextPreventivi.calcolaPreventivo(immobileContratto.getPrezzo(),
                    Integer.parseInt(immobileContratto.getLocali()), Integer.parseInt(immobileContratto.getMetriQuadri()));


        }

        return 10.0d;
    }
}
