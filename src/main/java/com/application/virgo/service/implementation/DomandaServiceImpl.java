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

    /**
     * Permette di pubblicare una domanda sotto un immobile
     * @param tempDomandaDTO dati relativi alla domanda
     * @param authUser utente autenticato
     * @return La domanda pubblicata
     * @throws UtenteException se l'utente autenticato non esiste
     */
    @Override
    public Optional<Domanda> addNewDomanda(DomandaDTO tempDomandaDTO, Utente authUser, Long idImmobile)
            throws UtenteException, ImmobileException {
        if(authUser != null){
            // creo la nuova domanda
            Domanda newDomanda = new Domanda(tempDomandaDTO.getContenuto(), Instant.now());

            newDomanda.setProprietarioDomanda(authUser);

          // prelevo l'immobile interessato
            Optional<Immobile> immobileInteressato = immobileService.getImmobileInternalInformationById(idImmobile);
            if(immobileInteressato.isPresent()){
                // aggiungo la domanda all'immobile
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

    /**
     * Permette di disabiliatare una domanda da parte dell'utente proprietario dell'immobile
     * @param auhtUser utente autenticato
     * @param idDomanda domanda da disabilitare
     * @return ritorna la domanda disabitata
     * @throws DomandaException se la domanda non esiste
     */
    @Override
    public Optional<Domanda> disabilitaDomanda(Utente auhtUser, Long idDomanda) throws DomandaException {
        // preleva la domanda
        Optional<Domanda> tempRequestedDomanda = domandaRepository.findByIdDomanda(idDomanda);
        if(tempRequestedDomanda.isPresent()){
            Domanda requestedDomanda = tempRequestedDomanda.get();
            if(requestedDomanda.getImmobileInteressato().getProprietario().getIdUtente().equals(auhtUser.getIdUtente())){
                // se l'utente è proprietario immobile allora disabilita domanda
                requestedDomanda.setIsEnabled(Boolean.FALSE);
                return Optional.of(domandaRepository.save(requestedDomanda));
            }else{
                throw new DomandaException("Non puoi disabilitare una domanda non tua");
            }
        }else{
            throw new DomandaException("Domanda non trovata, riprovare");
        }
    }


    /**
     * Permette di prelevare i dati di una domanda dal database
     * @param idDomanda id domanda da prelevare
     * @return la domanda richiesta
     * @throws DomandaException se la domanda non esiste
     */
    @Override
    public Optional<Domanda> getDomandainternalInformationById(Long idDomanda) throws DomandaException {
        Optional<Domanda> tempDomaanda = domandaRepository.getByIdDomanda(idDomanda);
        if (tempDomaanda.isPresent()){
            return tempDomaanda;
        }else{
            throw new DomandaException("Domanda non trovata");
        }
    }
}
