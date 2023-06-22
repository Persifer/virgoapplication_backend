package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.model.Immobile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class ImmobileInformationMapper implements Function<Immobile, GetImmobileInfoDTO> {

    @Override
    public GetImmobileInfoDTO apply(Immobile immobile) {

        GetImmobileInfoDTO immobileDto = new GetImmobileInfoDTO();

        immobileDto.setNomeProprietario(immobile.getProprietario().getNome() != null ? immobile.getProprietario().getNome() : "");
        immobileDto.setCognomeProprietario(immobile.getProprietario().getCognome() != null ? immobile.getProprietario().getCognome() : "");
        immobileDto.setEmail(immobile.getProprietario().getEmail() != null ? immobile.getProprietario().getEmail() : "");

        immobileDto.setIdImmobile(immobile.getIdImmobile() != null ? immobile.getIdImmobile():-1);

        immobileDto.setDescrizione(immobile.getDescrizione() != null ? immobile.getDescrizione() : "");
        immobileDto.setTitolo(immobile.getTitolo() != null ? immobile.getTitolo() : "");

        immobileDto.setDataUltimoRestauro(immobile.getDataUltimoRestauro() != null ? immobile.getDataUltimoRestauro() : null);
        immobileDto.setDataAcquisizione(immobile.getDataAcquisizione() != null ? immobile.getDataAcquisizione() : null);
        immobileDto.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : null);

        immobileDto.setCap(immobile.getCap() != null ? immobile.getCap() : "");
        immobileDto.setVia(immobile.getVia() != null ? immobile.getVia() : "");
        immobileDto.setProvincia(immobile.getProvincia() != null ? immobile.getProvincia() : "");
        immobileDto.setCitta(immobile.getCitta() != null ? immobile.getCitta() : "");

        if(immobile.getListaImmagini().isEmpty() || immobile.getListaImmagini().isBlank() || immobile.getListaImmagini() == null){
            immobileDto.setListaImmagini(List.of());
        }else{
            immobileDto.setListaImmagini(
                    Stream.of(immobile.getListaImmagini().split("/|"))
                            .collect(Collectors.toList())
            );
        }

        return immobileDto;
    }

}
