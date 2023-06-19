package com.application.virgo.service.implementation;

import com.application.virgo.DTO.Mapper.ContrattiUtenteMapper;
import com.application.virgo.DTO.outputDTO.ContrattiUtenteDTO;
import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.ContrattoUtenteJpaRepository;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.utilities.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ContrattoUtenteServiceImpl implements ContrattoUtenteService {

    private final ContrattoUtenteJpaRepository contrattoUtenteRepo;

    private final ContrattiUtenteMapper contrattiUtenteMapper;

    @Override
    public List<ContrattiUtenteDTO> getListaContrattiForUtente(Utente venditore, Long inidiceIniziale, Long pageSize)
            throws UtenteException, ContrattoUtenteException {
        if(venditore != null){
            if(inidiceIniziale < pageSize - contrattoUtenteRepo.countByVenditore(venditore) ){
                if(pageSize < Constants.PAGE_SIZE){
                    Page<ContrattoUtente> listContratti = contrattoUtenteRepo.getContrattoUtenteByVenditore(
                            PageRequest.of(inidiceIniziale.intValue(), pageSize.intValue()),
                            venditore
                            );
                    // converte con la stream una page di ContrattoUtente in una lista di ContrattiUtenteDTO
                    return listContratti.stream().map(contrattiUtenteMapper).collect(Collectors.toList());
                }else {
                    throw new ContrattoUtenteException("2 - Attenzione il numero dell'elemento da cui partire è troppo alto " + pageSize);
                }
            }else{
                throw new ContrattoUtenteException("1 -Attenzione il numero dell'elemento da cui partire è troppo alto");
            }
        }else{
            throw new UtenteException("Impossibile trovare l'utente autenticato");
        }
    }

    @Override

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
