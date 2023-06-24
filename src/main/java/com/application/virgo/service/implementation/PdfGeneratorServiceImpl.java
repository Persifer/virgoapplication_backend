package com.application.virgo.service.implementation;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.service.interfaces.PdfGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class PdfGeneratorServiceImpl implements PdfGeneratorService {

    private final ContrattoUtenteService contrattoUtenteService;


    @Override
    public void exportPDF(Utente authUser, Long idContratto) throws ContrattoException, ContrattoUtenteException {
        Optional<ContrattoUtente> contrattoUtente = contrattoUtenteService.getContrattoByIdUtenteAndIdContratto(authUser, idContratto);
        if(contrattoUtente.isPresent()){
            ContrattoUtente contratto = contrattoUtente.get();
        }
    }
}
