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
        immobileDto.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : null);

        immobileDto.setIsEnabled(immobile.getEnabled() != null ? immobile.getEnabled() : true);

        immobileDto.setCap(immobile.getCap() != null ? immobile.getCap() : "");
        immobileDto.setVia(immobile.getVia() != null ? immobile.getVia() : "");
        immobileDto.setProvincia(immobile.getProvincia() != null ? immobile.getProvincia() : "");
        immobileDto.setCitta(immobile.getCitta() != null ? immobile.getCitta() : "");

        return immobileDto;
    }
}