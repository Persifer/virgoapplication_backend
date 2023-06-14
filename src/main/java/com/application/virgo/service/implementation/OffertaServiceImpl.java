package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Offerta;
import com.application.virgo.repositories.ImmobileJpaRepository;
import com.application.virgo.repositories.OffertaJpaRepository;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.OffertaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OffertaServiceImpl implements OffertaService {


    private final OffertaJpaRepository offertaRepository;
    private final ImmobileService immobileService;

    @Override
    public Optional<Offerta> createNewOfferta(InsertOffertaDTO datiOfferta)
            throws OffertaException, ImmobileException {

        Optional<Immobile>  tempImmobileInteressato = immobileService.getImmobileInternalInformationById(datiOfferta.getIdImmobile());

        if(tempImmobileInteressato.isPresent()){
               Immobile immobileInteressato = tempImmobileInteressato.get();
            if(datiOfferta != null){

                if(datiOfferta.getPrezzoProposto() <= 0){
                    throw new OffertaException("Inserire un prezzo maggiore di zero");
                }

                if(datiOfferta.getCommento().isBlank() || datiOfferta.getCommento().isEmpty()){
                    datiOfferta.setCommento(""); // sovrascrivo così sono sicuro che ci siano dei dati consistenti
                }
                Offerta newOfferta = new Offerta(datiOfferta.getCommento(), datiOfferta.getPrezzoProposto());
                newOfferta.setIdImmobileInteressato(immobileInteressato);
                return Optional.of(offertaRepository.save(newOfferta));
            }else{
                throw new OffertaException("Impossibile inserire i dati di quest'offerta, riprovare");
            }
        }else{
           throw new ImmobileException("Impossibile utilizzare l'immobile selezionato");
        }
    }

    @Override
    public Optional<Offerta> getOffertaDetails(Long idOfferta) throws OffertaException {
        Optional<Offerta> requestedOfferta = offertaRepository.getOffertaByIdOfferta(idOfferta);

        if (requestedOfferta.isPresent()){
            return requestedOfferta;
        }else{
            throw new OffertaException("L'offerta selezionata non esiste");
        }
    }
}
