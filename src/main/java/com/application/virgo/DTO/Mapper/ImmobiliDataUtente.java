package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.GetUtenteImmobiliDTO;
import com.application.virgo.model.Immobile;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ImmobiliDataUtente implements Function<Immobile, GetUtenteImmobiliDTO> {


    @Override
    public GetUtenteImmobiliDTO apply(Immobile immobile) {

        GetUtenteImmobiliDTO immobileDto = new GetUtenteImmobiliDTO();

        immobileDto.setIdImmobile(immobile.getIdImmobile() != null ? immobile.getIdImmobile() : null);

        immobileDto.setDescrizione(immobile.getDescrizione() != null ? immobile.getDescrizione() : "");
        immobileDto.setTitolo(immobile.getTitolo() != null ? immobile.getTitolo() : "");

        immobileDto.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : 0.0f);

        immobileDto.setLocali(immobile.getLocali() != null ? immobile.getLocali() : "");
        immobileDto.setMetriQuadri(immobile.getMetriQuadri() != null ? immobile.getMetriQuadri() : "");

        immobileDto.setIsEnabled(immobile.getIsEnabled() != null ? immobile.getIsEnabled() : true);

        immobileDto.setCap(immobile.getCap() != null ? immobile.getCap() : "");
        immobileDto.setVia(immobile.getVia() != null ? immobile.getVia() : "");
        immobileDto.setProvincia(immobile.getProvincia() != null ? immobile.getProvincia() : "");
        immobileDto.setCitta(immobile.getCitta() != null ? immobile.getCitta() : "");



        return immobileDto;
    }
}
