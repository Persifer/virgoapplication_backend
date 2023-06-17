package com.application.virgo.service.implementation;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Immobile;
import com.application.virgo.repositories.ContrattoJpaRepository;
import com.application.virgo.service.interfaces.ContrattoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ContrattoServiceImpl implements ContrattoService {

    private final ContrattoJpaRepository contrattoRepo;

    @Override
    public Optional<Contratto> createNewContratto(Immobile immobileInteressato, Float prezzoFinale)
            throws ImmobileException {

        if(immobileInteressato!=null){
            Contratto newContratto = new Contratto();

            newContratto.setPrezzo(prezzoFinale);
            newContratto.setDataStipulazione(Instant.now());
            newContratto.setImmobileInteressato(immobileInteressato);

            return Optional.of(contrattoRepo.save(newContratto));
        }else{
            throw new ImmobileException("L'immobile selezionato non esiste");
        }
    }
}
