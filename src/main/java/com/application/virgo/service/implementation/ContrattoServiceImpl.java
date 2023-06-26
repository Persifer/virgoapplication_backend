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
    /**
     * Metodo che permette la creazione di un nuovo contratto a seguito di un un'offerta accettata
     * @param immobileInteressato l'entità immobile che è interessata nell'offerta
     * @param prezzoFinale prezzo finale proposto nell'offerta
     * @return il contratto creato tra due utenti
     * @throws ImmobileException se l'immobile non esiste
     */
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

    @Override
    public Optional<Contratto> getContrattoById(Long contratto) throws ContrattoException {
        if(contratto != null){
            Optional<Contratto> wantedContratto = contrattoRepo.getContrattoByIdContratto(contratto);
            if(wantedContratto.isPresent()){
                return wantedContratto;
            }else{
                throw new ContrattoException("Impossibile trovare il contratto");
            }
        }else{
            throw new ContrattoException("Inserire un id contratto valido");
        }
    }
}
