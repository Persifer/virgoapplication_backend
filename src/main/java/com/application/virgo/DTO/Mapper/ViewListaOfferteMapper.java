package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.ViewListaOfferteDTO;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ViewListaOfferteMapper implements Function<OfferteUtente, ViewListaOfferteDTO> {
    @Override
    public ViewListaOfferteDTO apply(OfferteUtente offerteUtente) {
        ViewListaOfferteDTO viewListaOfferteDTO = new ViewListaOfferteDTO();

        viewListaOfferteDTO.setNomeUtente(offerteUtente.getOfferente().getNome());
        viewListaOfferteDTO.setCognomeUtente(offerteUtente.getOfferente().getCognome());

        viewListaOfferteDTO.setIdOfferente(offerteUtente.getOfferente().getIdUtente());
        viewListaOfferteDTO.setIdOfferta(offerteUtente.getOffertaInteressata().getIdOfferta());
        viewListaOfferteDTO.setIdImmobile(offerteUtente.getOffertaInteressata().getIdImmobileInteressato().getIdImmobile());

        return viewListaOfferteDTO;
    }
}
