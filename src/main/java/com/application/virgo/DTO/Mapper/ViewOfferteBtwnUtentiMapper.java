package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.ViewOfferteBetweenUtentiDTO;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

import static com.application.virgo.utilities.Constants.FORMATTER;

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
                FORMATTER.format(offerteUtente.getData_proposta()) : FORMATTER.format(Instant.now()));

        offertaDTO.setPrezzo(offerteUtente.getOffertaInteressata().getPrezzoProposto() != null ?
                offerteUtente.getOffertaInteressata().getPrezzoProposto().toString() : "--");

        offertaDTO.setInviatoDaProprietario(offerteUtente.getMadeByProprietario());

        offertaDTO.setCommento(offerteUtente.getOffertaInteressata().getCommento());

        if(offerteUtente.getData_declino() != null){
            offertaDTO.setDataDeclino(offerteUtente.getData_declino().toString());
            offertaDTO.setIsDeclinato(Boolean.TRUE);

            offertaDTO.setIsAccettato(Boolean.FALSE);
            offertaDTO.setDataAccettazione("");
        }else{
            offertaDTO.setDataDeclino("");
            offertaDTO.setIsDeclinato(Boolean.FALSE);
        }

        if(offerteUtente.getData_accettazione() != null){
            offertaDTO.setDataAccettazione(offerteUtente.getData_accettazione().toString());
            offertaDTO.setIsAccettato(Boolean.TRUE);

            offertaDTO.setIsDeclinato(Boolean.FALSE);
            offertaDTO.setDataDeclino("");
        }else{
            offertaDTO.setDataAccettazione("");
            offertaDTO.setIsDeclinato(Boolean.FALSE);
        }


        return offertaDTO;
    }
}
