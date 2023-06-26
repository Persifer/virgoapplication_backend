package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaException;
import com.application.virgo.exception.UtenteException;
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
    /**
     * Metodo che permette la memorizzazione di un'offerta da parte di un utente
     * @param datiOfferta dettagli dell'offerta inserita dall'utente
     * @return Un optional contenente l'offerta creata da un utente
     * @throws OffertaException nel caso in cui non è stato possibile creare l'offerta
     * @throws ImmobileException se non trova l'immobile richiesto
     */
    public Optional<Offerta> createNewOfferta(InsertOffertaDTO datiOfferta)
            throws OffertaException, ImmobileException {

        // prelevo le informazioni dell'immobile interessato
        Optional<Immobile>  tempImmobileInteressato = immobileService.getImmobileInternalInformationById(datiOfferta.getIdImmobile());

        if(tempImmobileInteressato.isPresent()){
               Immobile immobileInteressato = tempImmobileInteressato.get();
            if(datiOfferta != null){
                // manipolo i dati che mi serono
                if(datiOfferta.getPrezzoProposto() <= 0){
                    throw new OffertaException("Inserire un prezzo maggiore di zero");
                }

                if(datiOfferta.getCommento().isBlank() || datiOfferta.getCommento().isEmpty()){
                    datiOfferta.setCommento(""); // sovrascrivo così sono sicuro che ci siano dei dati consistenti
                }
                Offerta newOfferta = new Offerta(datiOfferta.getCommento(), datiOfferta.getPrezzoProposto());
                newOfferta.setIdImmobileInteressato(immobileInteressato);
                // creo una nuova offerta e la salvo
                return Optional.of(offertaRepository.save(newOfferta));
            }else{
                throw new OffertaException("Impossibile inserire i dati di quest'offerta, riprovare");
            }
        }else{
           throw new ImmobileException("Impossibile utilizzare l'immobile selezionato");
        }
    }

    @Override
    /**
     * Permette di ottenere i dati di una determinata offerta
     * @param idOfferta è l'id dell'offerta di cui vogliamo ottenere informazioni
     * @return Un optional contenente l'offerta richiesta al backend
     * @throws OffertaException nel caso in cui non è stato possibile trovare l'offerta
     */
    public Optional<Offerta> getOffertaDetails(Long idOfferta) throws OffertaException {

        Optional<Offerta> requestedOfferta = offertaRepository.getOffertaByIdOfferta(idOfferta);

        if (requestedOfferta.isPresent()){
            return requestedOfferta;
        }else{
            throw new OffertaException("L'offerta selezionata non esiste");
        }
    }
}
