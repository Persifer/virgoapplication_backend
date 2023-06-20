package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.ViewOfferteBetweenUtentiDTO;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Function;

@Component
public class ViewOfferteBtwnUtentiMapper implements Function<OfferteUtente, ViewOfferteBetweenUtentiDTO> {

    @Override
    public ViewOfferteBetweenUtentiDTO apply(OfferteUtente offerteUtente) {

        ViewOfferteBetweenUtentiDTO offertaDTO = new ViewOfferteBetweenUtentiDTO();

        offertaDTO.setIdImmobile(offerteUtente.getOffertaInteressata().getIdImmobileInteressato().getIdImmobile() != null ?
                offerteUtente.getOffertaInteressata().getIdImmobileInteressato().getIdImmobile() : -1);

        offertaDTO.setIdOfferta(offerteUtente.getOffertaInteressata().getIdOfferta() != null ?
                offerteUtente.getOffertaInteressata().getIdOfferta() : -1);

        offertaDTO.setIdPropietario(offerteUtente.getProprietario().getIdUtente() != null ?
                offerteUtente.getProprietario().getIdUtente() : -1);

        offertaDTO.setIdOfferente(offerteUtente.getOfferente().getIdUtente() != null ?
                offerteUtente.getOfferente().getIdUtente() : -1);


        offertaDTO.setNomeOfferente(offerteUtente.getOfferente().getNome() != null ?
                offerteUtente.getOfferente().getNome() : "");

        offertaDTO.setCognomeOfferente(offerteUtente.getOfferente().getCognome() != null ?
                offerteUtente.getOfferente().getCognome() : "");

        offertaDTO.setDataOfferta(offerteUtente.getData_proposta() != null ?
                offerteUtente.getData_proposta().toString() : Instant.now().toString());

        offertaDTO.setPrezzo(offerteUtente.getOffertaInteressata().getPrezzoProposto() != null ?
                offerteUtente.getOffertaInteressata().getPrezzoProposto().toString() : "--");

        offertaDTO.setInviatoDaProprietario(offerteUtente.getMadeByProprietario());

        if(offerteUtente.getData_declino() != null){
            offertaDTO.setDataDeclino(offerteUtente.getData_declino().toString());
            offertaDTO.setIsDeclinato(true);

            offertaDTO.setIsAccettato(false);
            offertaDTO.setDataAccettazione("");
        }else{
            offertaDTO.setDataDeclino("");
            offertaDTO.setIsDeclinato(null);
        }

        if(offerteUtente.getData_accettazione() != null){
            offertaDTO.setDataAccettazione(offerteUtente.getData_accettazione().toString());
            offertaDTO.setIsAccettato(true);

            offertaDTO.setIsDeclinato(false);
            offertaDTO.setDataDeclino("");
        }else{
            offertaDTO.setDataAccettazione("");
            offertaDTO.setIsDeclinato(null);
        }


        return offertaDTO;
    }
}
