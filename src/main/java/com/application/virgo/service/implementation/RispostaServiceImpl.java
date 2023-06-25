package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.RispostaDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Risposta;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.RispostaJpaRepository;
import com.application.virgo.service.interfaces.DomandaService;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.RispostaService;
import com.application.virgo.service.interfaces.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RispostaServiceImpl implements RispostaService {

    private final ImmobileService immobileService;
    private final DomandaService domandaService;
    private final RispostaJpaRepository rispostaRepository;

    @Override
    public Optional<Risposta> addNewRisposta(RispostaDTO tempNewRisposta, Long idDomanda, Utente authUser, Long idImmobile)
            throws ImmobileException, UtenteException, DomandaException {
        Optional<Immobile> tempImmobileInteressato = immobileService.getImmobileInternalInformationById(idImmobile);
        if(tempImmobileInteressato.isPresent()){
            Immobile immobileInteressato = tempImmobileInteressato.get();

            // solo il proprietario può rispondere alle domande
            if(immobileInteressato.getProprietario().getIdUtente().equals(authUser.getIdUtente())){

                Risposta newRisposta = new Risposta(tempNewRisposta.getContenuto(), Instant.now());
                newRisposta.setProprietarioRisposta(authUser);
                newRisposta.setIsEnabled(Boolean.TRUE);

                Optional<Domanda> getDomanda = domandaService.getDomandainternalInformationById(idDomanda);
                getDomanda.ifPresent(newRisposta::setDomandaDiRiferimento);

                return Optional.of(rispostaRepository.save(newRisposta));
            }else{
                throw new UtenteException("Solo l'utente proprietario può rispondere alle domande in quanto l'immobile è il suo");
            }
        }else {
            throw new ImmobileException("L'immobile cercato non esiste, selezionarne uno nuovo");
        }
    }
}
