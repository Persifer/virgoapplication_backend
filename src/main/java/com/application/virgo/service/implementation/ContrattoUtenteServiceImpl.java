package com.application.virgo.service.implementation;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.ContrattoUtenteJpaRepository;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class ContrattoUtenteServiceImpl implements ContrattoUtenteService {

    private final ContrattoUtenteJpaRepository contrattoUtenteRepo;

    @Override
    /**
     * Metodo che serve per la memorizzazione di un nuovo contratto tra due utenti
     */
    public Optional<ContrattoUtente> saveContrattoBetweenUtenti(Utente venditore, Utente acquirente, Contratto contrattoInteressato)
            throws ContrattoUtenteException, ContrattoException {
        if(venditore != null){
            if(acquirente != null){
                if(contrattoInteressato!=null){

                    ContrattoUtente newContrattoUtente = new ContrattoUtente();

                    newContrattoUtente.setContrattoInteressato(contrattoInteressato);
                    newContrattoUtente.setAcquirente(acquirente);
                    newContrattoUtente.setVenditore(venditore);

                    newContrattoUtente.setIdContrattoUtente(
                            new ContrattoUtenteCompoundKey(
                                    venditore.getIdUtente(),
                                    acquirente.getIdUtente(),
                                    contrattoInteressato.getIdContratto())
                    );

                    return Optional.of(contrattoUtenteRepo.save(newContrattoUtente));
                }else{
                    throw new ContrattoException("Impossibile reperire il contratto da assegnare");
                }
            }else{
                throw new ContrattoUtenteException("Impossibile reperire l'acquirente per creare il contratto");
            }
        }else{
            throw new ContrattoUtenteException("Impossibile reperire il venditore per creare il contratto");
        }

    }
}
