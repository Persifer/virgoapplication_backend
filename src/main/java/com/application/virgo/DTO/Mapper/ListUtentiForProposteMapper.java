package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.outputDTO.ListUtentiForProposteDTO;
import com.application.virgo.model.Utente;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ListUtentiForProposteMapper implements Function<Utente, ListUtentiForProposteDTO> {

    @Override
    public ListUtentiForProposteDTO apply(Utente utente) {
        ListUtentiForProposteDTO newListUtenteDTO = new ListUtentiForProposteDTO();

        newListUtenteDTO.setIdOfferente(utente.getIdUtente());

        newListUtenteDTO.setNomeUtente(utente.getNome());
        newListUtenteDTO.setCognomeUtente(utente.getCognome());

        return newListUtenteDTO;
    }
}
