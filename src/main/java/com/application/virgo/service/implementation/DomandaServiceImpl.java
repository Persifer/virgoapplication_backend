package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.DomandaJpaRepository;
import com.application.virgo.service.interfaces.DomandaService;
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
    @Override
    public Optional<Domanda> addNewDomanda(DomandaDTO tempDomandaDTO, Utente authUser)
            throws UtenteException {
        if(authUser != null){
            // creo la nuova domanda
            Domanda newDomanda = new Domanda(tempDomandaDTO.getContenuto(), Instant.now());
            newDomanda.setProprietarioDomanda(authUser);

            // creo la nuova domanda
            return Optional.of(domandaRepository.save(newDomanda));
        }else{
            throw new UtenteException("Bisogna essere loggati per poter pubblicare una domanda");
        }
    }
}
