package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.DomandaImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.application.virgo.utilities.Util.getListaImamginiFromString;

@Component
public class ImmobileInformationMapper implements Function<Immobile, GetImmobileInfoDTO> {

    @Autowired
    private final DomandaImmobileMapper mapperDomande;

    public ImmobileInformationMapper(DomandaImmobileMapper domandaImmobileMapper) {
        this.mapperDomande = domandaImmobileMapper;
    }

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
        immobileDto.setDataCreazioneImmobile(immobile.getDataCreazioneImmobile() != null ? immobile.getDataCreazioneImmobile() : null);
        immobileDto.setPrezzo(immobile.getPrezzo() != null ? immobile.getPrezzo() : null);

        immobileDto.setCap(immobile.getCap() != null ? immobile.getCap() : "");
        immobileDto.setVia(immobile.getVia() != null ? immobile.getVia() : "");
        immobileDto.setProvincia(immobile.getProvincia() != null ? immobile.getProvincia() : "");
        immobileDto.setCitta(immobile.getCitta() != null ? immobile.getCitta() : "");

        immobileDto.setIdProprietario(immobile.getProprietario().getIdUtente());
        immobileDto.setLocali(immobile.getLocali() != null ? immobile.getLocali() : "");
        immobileDto.setMetriQuadri(immobile.getMetriQuadri() != null ? immobile.getMetriQuadri() : "");

        immobileDto.setListaImmagini(getListaImamginiFromString(immobile.getListaImmagini()));

        List<DomandaImmobileDTO> domandeImmobile = new ArrayList<>();

        for(Domanda domanda : immobile.getDomandeImmobile()){
            domandeImmobile.add(mapperDomande.apply(domanda));
        }


        immobileDto.setListaDomandeImmobile(domandeImmobile);
        return immobileDto;
    }

}
