package com.application.virgo.service.implementation;

import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.CompoundKey.OffertaUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.OffertaUtenteJpaRepository;
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

    private final UtenteService utenteService;
    private final OffertaUtenteJpaRepository offertaUtenteRepository;

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

        Optional<Utente> utenteProprietario = utenteService.getUtenteClassById(idVenditore);
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
    public List<OfferteUtente> allOfferteBetweenUtenti(Utente authUser, Utente offerente) throws UtenteException {
        if (authUser != null){
            if(offerente != null){
                return offertaUtenteRepository.getAllOfferteBetweenUtenti(authUser.getIdUtente(), offerente.getIdUtente());
            }else{
                throw new UtenteException("Impossibile reperire l'utente che ha proposto le offerte");
            }
        }else{
            throw new UtenteException("Impossibile reperire l'utente autenticato");
        }
    }
}
