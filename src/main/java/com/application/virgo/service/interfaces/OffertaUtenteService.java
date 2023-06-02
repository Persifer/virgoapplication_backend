package com.application.virgo.service.interfaces;

import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface OffertaUtenteService {

    public Optional<OfferteUtente> saveOffertaToUtente(Utente offerente, Offerta offertaProposta, Long idVenditore)
            throws UtenteException;
}
