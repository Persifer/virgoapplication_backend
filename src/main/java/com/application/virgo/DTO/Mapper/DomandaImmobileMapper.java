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

        domandaDTO.setNomeProprietario(domanda.getProprietarioDomanda().getNome());
        domandaDTO.setCognomeProprietario(domanda.getProprietarioDomanda().getCognome());

        domandaDTO.setContenuto(domanda.getContenuto());
        domandaDTO.setDataPubblicazione(domanda.getDataPubblicazione().toString());

        return domandaDTO;
    }
}
