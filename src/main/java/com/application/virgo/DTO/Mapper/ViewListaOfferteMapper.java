package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.ViewListaOfferte;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Utente;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ViewListaOfferteMapper implements Function<OfferteUtente, ViewListaOfferte> {
    @Override
    public ViewListaOfferte apply(OfferteUtente offerteUtente) {
        ViewListaOfferte viewListaOfferte = new ViewListaOfferte();

        viewListaOfferte.setNomeOfferente(offerteUtente.getOfferente().getNome());
        viewListaOfferte.setCognomeOfferente(offerteUtente.getOfferente().getCognome());

        viewListaOfferte.setIdOfferente(offerteUtente.getOfferente().getIdUtente());
        viewListaOfferte.setIdImmobile(offerteUtente.getOffertaInteressata().getIdImmobileInteressato().getIdImmobile());

        return viewListaOfferte;
    }
}
