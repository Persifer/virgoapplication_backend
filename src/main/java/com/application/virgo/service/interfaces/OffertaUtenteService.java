package com.application.virgo.service.interfaces;

import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OffertaUtenteService {

    public Optional<OfferteUtente> saveOffertaToUtente(Utente offerente, Offerta offertaProposta, Long idVenditore)
            throws UtenteException;

    public Page<OfferteUtente> getOfferteForUtenteProprietario(Utente offerente, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException;

    public List<OfferteUtente> allOfferteBetweenUtenti(Utente proprietario, Utente offerente, Long idImmobile)
            throws UtenteException, ImmobileException;

    public Optional<OfferteUtente> acceptOfferta(Long idOfferta, Utente authUser)
            throws OffertaException, OffertaUtenteException, UtenteException;

    public Optional<OfferteUtente> declineOfferta(Long idOfferta, Utente authUser)
            throws OffertaException, OffertaUtenteException, UtenteException;

    public Page<OfferteUtente> getOfferteProposte(Utente offerente, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException;
}
