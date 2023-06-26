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
    
    @Override
    public Double calcolaPreventivoImmobile(Long idContratto, Integer selettoreAzienda)
            throws ContrattoException, PreventivoException, NumberFormatException {
        Optional<Contratto> tempContratto = contrattoService.getContrattoById(idContratto);
        if(tempContratto.isPresent()){
            Contratto contratto = tempContratto.get();
            Immobile immobileContratto = contratto.getImmobileInteressato();

            ContextPreventivi contextPreventivi = new ContextPreventivi();
            switch (selettoreAzienda){
                case 1:
                    contextPreventivi.setAlgoritmo(new AlgoritmoAziendaXYZ());
                    break;
                case 2:
                    contextPreventivi.setAlgoritmo(new AlgoritmoAziendaMPT());
                    break;
                default:
                    throw new PreventivoException("L'azienda selezionata non esiste");
            }

            return contextPreventivi.calcolaPreventivo(immobileContratto.getPrezzo(),
                    Integer.parseInt(immobileContratto.getLocali()), Integer.parseInt(immobileContratto.getMetriQuadri()));
        }

        return 0.0d;
    }
}
