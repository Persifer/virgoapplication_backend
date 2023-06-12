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

        viewListaOfferteDTO.setNomeOfferente(offerteUtente.getOfferente().getNome());
        viewListaOfferteDTO.setCognomeOfferente(offerteUtente.getOfferente().getCognome());

        viewListaOfferteDTO.setIdOfferente(offerteUtente.getOfferente().getIdUtente());
        viewListaOfferteDTO.setIdImmobile(offerteUtente.getOffertaInteressata().getIdImmobileInteressato().getIdImmobile());

        return viewListaOfferteDTO;
    }
}
