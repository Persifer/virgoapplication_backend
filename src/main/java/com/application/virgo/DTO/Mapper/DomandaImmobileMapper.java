package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.DomandaImmobileDTO;
import com.application.virgo.model.Domanda;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DomandaImmobileMapper implements Function<Domanda, DomandaImmobileDTO> {
    @Override
    public DomandaImmobileDTO apply(Domanda domanda) {
        DomandaImmobileDTO domandaDTO = new DomandaImmobileDTO();

        domandaDTO.setIdDomanda(domanda.getIdDomanda());
        domandaDTO.setNomeProprietario(domanda.getProprietarioDomanda().getNome());
        domandaDTO.setCognomeProprietario(domanda.getProprietarioDomanda().getCognome());

        domandaDTO.setContenuto(domanda.getContenuto());
        domandaDTO.setDataPubblicazione(domanda.getDataPubblicazione().toString());

        if(domanda.getRisposta() == null || domanda.getRisposta().getContenuto().isEmpty()
                || domanda.getRisposta().getContenuto().isBlank()){
            domandaDTO.setRisposta("");
            domandaDTO.setDataPubblicazione("");
        }else{
            domandaDTO.setRisposta(domanda.getRisposta().getContenuto());
            domandaDTO.setDataRisposta(domanda.getRisposta().getDataPubblicazione().toString());
        }



        return domandaDTO;
    }
}
