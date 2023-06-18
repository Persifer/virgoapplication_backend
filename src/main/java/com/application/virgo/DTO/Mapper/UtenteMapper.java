package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.model.Utente;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Component
public class UtenteMapper implements Function<Utente, UtenteDTO> {
    @Override
    public UtenteDTO apply(Utente utente) {
        return new UtenteDTO(
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getDataNascita()
                );
    }

    public Utente apply(UtenteDTO utente) {
        return new Utente(
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getPassword(),
                utente.getDataNascita()
        );
    }
}
