package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.HomeImmobileDTO;
import com.application.virgo.model.Immobile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static com.application.virgo.utilities.Util.getListaImamginiFromString;
import static com.application.virgo.utilities.Util.getSingleImageFromList;

@Component
public class HomeImmobileMapper implements Function<Immobile, HomeImmobileDTO> {

    @Override
    public HomeImmobileDTO apply(Immobile immobile){

        HomeImmobileDTO immobileDto=new HomeImmobileDTO();

        immobileDto.setIdImmobile(immobile.getIdImmobile()!=null?immobile.getIdImmobile():-1);

        immobileDto.setDescrizione(immobile.getDescrizione()!=null?immobile.getDescrizione():"");
        immobileDto.setTitolo(immobile.getTitolo()!=null?immobile.getTitolo():"");

        immobileDto.setPrezzo(immobile.getPrezzo()!=null?immobile.getPrezzo():null);

        immobileDto.setImmagineImmobile(getSingleImageFromList(immobile.getListaImmagini()));


        return immobileDto;
    }
}
