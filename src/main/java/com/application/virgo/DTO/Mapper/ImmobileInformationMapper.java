package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.model.Immobile;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ImmobileInformationMapper implements Function<Immobile, GetImmobileInfoDTO> {

    @Override
    public GetImmobileInfoDTO apply(Immobile immobile) {

        GetImmobileInfoDTO immobileDto = new GetImmobileInfoDTO();

        immobileDto.setNomeProprietario(immobile.getProprietario().getNome() != null ? immobile.getProprietario().getNome() : "");
        immobileDto.setNomeProprietario(immobile.getProprietario().getCognome() != null ? immobile.getProprietario().getCognome() : "");
        immobileDto.setEmail(immobile.getProprietario().getEmail() != null ? immobile.getProprietario().getEmail() : "");

        immobileDto.setDescrizione(immobile.getDescrizione() != null ? immobile.getDescrizione() : null);
        immobileDto.setDataUltimoRestauro(immobile.getDataUltimoRestauro() != null ? immobile.getDataUltimoRestauro() : null);
        immobileDto.setDataAcquisizione(immobile.getDataAcquisizione() != null ? immobile.getDataAcquisizione() : null);
        immobileDto.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : null);

        immobileDto.setCap(immobile.getCap() != null ? immobile.getCap() : "");
        immobileDto.setVia(immobile.getVia() != null ? immobile.getVia() : "");
        immobileDto.setProvincia(immobile.getProvincia() != null ? immobile.getProvincia() : "");
        immobileDto.setCitta(immobile.getCitta() != null ? immobile.getCitta() : "");


        return immobileDto;
    }

}
