package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.CompoundKey.OffertaUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.OffertaUtenteJpaRepository;
import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.OffertaService;
import com.application.virgo.service.interfaces.OffertaUtenteService;
import com.application.virgo.service.interfaces.UtenteService;
import com.application.virgo.utilities.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OffertaUtenteServiceImpl implements OffertaUtenteService{


    private final UtenteJpaRepository utenteRepo;
    private final OffertaUtenteJpaRepository offertaUtenteRepository;
    private final ImmobileService immobileService;
    private final OffertaService offertaService;

    private Optional<Utente> getInformationUtente(Long idUtenteToFound) throws UtenteException{
        Optional<Utente> tempUtente = utenteRepo.getUtenteByIdUtente(idUtenteToFound);
        if(tempUtente.isPresent()){
            return tempUtente;
        }else{
            throw new UtenteException("L'utente selezionato non esiste, se ne inserica un altro");
        }

    }

    /**
     *
     * @param offerente dettagli dell'utente che ha proposto l'offerta
     * @param offertaProposta dettagli dell'offerta proposta da un utente
     * @param idVenditore id dell'utente che ha messo in vendita l'immobile
     * @return Un optional contenente l'offerta creata da un utente
     * @throws UtenteException nel caso in cui non trova l'utente proprietario
     */
    @Override
    public Optional<OfferteUtente> saveOffertaToUtente(Utente offerente, Offerta offertaProposta, Long idVenditore)
        throws UtenteException {

        Optional<Utente> utenteProprietario = getInformationUtente(idVenditore);
        if(utenteProprietario.isPresent()){

            OffertaUtenteCompoundKey compoundKeyProprietario = new OffertaUtenteCompoundKey(utenteProprietario.get().getIdUtente(),
                    offerente.getIdUtente(),
                    offertaProposta.getIdOfferta());


            OfferteUtente offertaToProprietario = new OfferteUtente(compoundKeyProprietario,
                    utenteProprietario.get(), offerente,offertaProposta);


            return Optional.of(offertaUtenteRepository.save(offertaToProprietario));

        }
        return Optional.empty();
    }

    @Override
    public Page<OfferteUtente> getOfferteForUtenteProprietario(Utente authUser, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException {

        if(authUser != null){
            if(pageSize < Constants.PAGE_SIZE){
                return offertaUtenteRepository.getAllOfferteUtenteAsProprietario(
                        PageRequest.of(offset.intValue(), pageSize.intValue()), authUser.getIdUtente()
                );
            }else{
                throw new OffertaUtenteException("La grandezza della pagina supera i " + Constants.PAGE_SIZE + " elementi");
            }
        }else{
            throw new UtenteException("L'utente deve essere autenticato");
        }

    }


    @Override
    public List<OfferteUtente> allOfferteBetweenUtenti(Utente authUser, Utente offerente, Long idImmobile)
            throws UtenteException, ImmobileException {
        if (authUser != null){
            if(offerente != null){
                Optional<Immobile> immobile = immobileService.getImmobileInternalInformationById(idImmobile);
                if(immobile.isPresent()){
                    return offertaUtenteRepository.getAllOfferteBetweenUtenti(authUser.getIdUtente(), offerente.getIdUtente(), immobile.get().getIdImmobile());

                }else{
                    throw new ImmobileException("Immobile insesistente");
                }
            }else{
                throw new UtenteException("Impossibile reperire l'utente che ha proposto le offerte");
            }
        }else{
            throw new UtenteException("Impossibile reperire l'utente autenticato");
        }
    }

    private Optional<OfferteUtente> methodForAcceptAndDenyOfferta(Long idOfferta, Utente authUser, Boolean isAccettata)
            throws OffertaException, OffertaUtenteException, UtenteException{
        if(authUser != null){
            Optional<Offerta> offertaSelezionata = offertaService.getOffertaDetails(idOfferta);
            if(offertaSelezionata.isPresent()){
                Optional<OfferteUtente> tempOffertaToAccept =
                        offertaUtenteRepository.getOfferteUtenteByProprietarioAndOffertaInteressata(authUser,offertaSelezionata.get());
                if(tempOffertaToAccept.isPresent()){

                    OfferteUtente offertaToAccept = tempOffertaToAccept.get();
                    offertaToAccept.setIsAccettato(isAccettata);
                    offertaToAccept.setIsDeclinato(!isAccettata);

                    //SE TRUE ALLORA HO ACCETTATO,
                    if(isAccettata){
                        offertaToAccept.setData_accettazione(Instant.now());
                    }else{
                        offertaToAccept.setData_declino(Instant.now());
                    }

                    return Optional.of(offertaUtenteRepository.save(offertaToAccept));
                }else{
                    throw new OffertaUtenteException("La richiesta di offerta non esiste!");
                }
            }else{
                throw new OffertaException("L'offerta non esiste!");
            }
        }else{
            throw new UtenteException("Bisogna essere autenticati per effettuare quest'azione!");
        }
    }


    @Override
    public Optional<OfferteUtente> acceptOfferta(Long idOfferta, Utente authUser)
            throws OffertaException, OffertaUtenteException, UtenteException {

        return methodForAcceptAndDenyOfferta(idOfferta, authUser, true);

    }

    @Override
    public Optional<OfferteUtente> declineOfferta(Long idOfferta, Utente authUser)
            throws OffertaException, OffertaUtenteException, UtenteException {
        return methodForAcceptAndDenyOfferta(idOfferta, authUser, false);
    }
}
