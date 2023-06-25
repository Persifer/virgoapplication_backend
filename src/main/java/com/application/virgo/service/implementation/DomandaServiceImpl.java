package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.RispostaException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Risposta;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.DomandaJpaRepository;
import com.application.virgo.service.interfaces.DomandaService;
import com.application.virgo.service.interfaces.ImmobileService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class DomandaServiceImpl implements DomandaService {

    private final DomandaJpaRepository domandaRepository;
    private final ImmobileService immobileService;
    @Override
    public Optional<Domanda> addNewDomanda(DomandaDTO tempDomandaDTO, Utente authUser, Long idImmobile)
            throws UtenteException, ImmobileException {
        if(authUser != null){
            // creo la nuova domanda
            Domanda newDomanda = new Domanda(tempDomandaDTO.getContenuto(), Instant.now());

            newDomanda.setProprietarioDomanda(authUser);


            Optional<Immobile> immobileInteressato = immobileService.getImmobileInternalInformationById(idImmobile);
            if(immobileInteressato.isPresent()){
                newDomanda.setImmobileInteressato(immobileInteressato.get());
                newDomanda.setIsEnabled(Boolean.TRUE);
                // creo la nuova domanda
                return Optional.of(domandaRepository.save(newDomanda));
            }else{
                throw new ImmobileException("Nessun immobile");
            }
        }else{
            throw new UtenteException("Bisogna essere loggati per poter pubblicare una domanda");
        }
    }

    @Override
    public Optional<Domanda> disabilitaDomanda(Utente auhtUser, Long idDomanda) throws DomandaException {
        Optional<Domanda> tempRequestedDomanda = domandaRepository.findByIdDomanda(idDomanda);
        if(tempRequestedDomanda.isPresent()){
            Domanda requestedDomanda = tempRequestedDomanda.get();
            if(requestedDomanda.getImmobileInteressato().getProprietario().getIdUtente().equals(auhtUser.getIdUtente())){
                requestedDomanda.setIsEnabled(Boolean.FALSE);
                return Optional.of(domandaRepository.save(requestedDomanda));
            }else{
                throw new DomandaException("Non puoi disabilitare una domanda non tua");
            }
        }else{
            throw new DomandaException("Domanda non trovata, riprovare");
        }
    }

    @Override
    public Optional<Domanda> replyToDomanda(Risposta risposta, Long idDomanda)
            throws DomandaException, UtenteException, RispostaException {
        Optional<Domanda> tempDomandaInteressata = domandaRepository.findByIdDomanda(idDomanda);

        if(tempDomandaInteressata.isPresent()){
            Domanda domandaInteressata = tempDomandaInteressata.get();
            if(risposta!=null){
                domandaInteressata.setRisposta(risposta);
                return Optional.of(domandaRepository.save(domandaInteressata));
            }else {
                throw new RispostaException("Impossibile trovare la risposta, riprovare");
            }

        }else{
            throw new DomandaException("La domanda selezionata non esiste, sceglierne un'altra");
        }
    }
}
