package com.application.virgo.service.implementation;

import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.CompoundKey.OffertaUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.OffertaUtenteJpaRepository;
import com.application.virgo.service.interfaces.OffertaUtenteService;
import com.application.virgo.service.interfaces.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OffertaUtenteServiceImpl implements OffertaUtenteService{

    private final UtenteService utenteService;
    private final OffertaUtenteJpaRepository offertaUtenteRepository;

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
}
