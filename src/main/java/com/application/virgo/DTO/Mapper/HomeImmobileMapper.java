package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.HomeImmobileDTO;
import com.application.virgo.model.Immobile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class HomeImmobileMapper implements Function<Immobile, HomeImmobileDTO> {

    @Override
    public HomeImmobileDTO apply(Immobile immobile){

        HomeImmobileDTO immobileDto=new HomeImmobileDTO();

        immobileDto.setIdImmobile(immobile.getIdImmobile()!=null?immobile.getIdImmobile():-1);

        immobileDto.setDescrizione(immobile.getDescrizione()!=null?immobile.getDescrizione():"");
        immobileDto.setTitolo(immobile.getTitolo()!=null?immobile.getTitolo():"");

        immobileDto.setPrezzo(immobile.getPrezzo()!=null?immobile.getPrezzo():null);

        if(immobile.getListaImmagini()== null ||
                immobile.getListaImmagini().isEmpty() || immobile.getListaImmagini().isBlank()){
            immobileDto.setImmagineImmobile("C:\\progetto_ing_sw\\files\\no_image.jpg");
        }else{
            immobileDto.setImmagineImmobile(
                    List.of(immobile.getListaImmagini().split("\\|")).get(0)
            );
        }


        return immobileDto;
    }
}
