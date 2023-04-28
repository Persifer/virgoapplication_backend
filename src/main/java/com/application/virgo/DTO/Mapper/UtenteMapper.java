package com.application.virgo.DTO.Mapper;

import com.application.virgo.DTO.UtenteDTO;
import com.application.virgo.model.Utente;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UtenteMapper implements Function<Utente, UtenteDTO> {
    @Override
    public UtenteDTO apply(Utente utente) {
        return new UtenteDTO(
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getVia(),
                utente.getCap(),
                utente.getCitta(),
                utente.getProvincia(),
                utente.getDataNascita()
                );
    }

    public Utente apply(UtenteDTO utente) {
        return new Utente(
                utente.getNome(),
                utente.getCognome(),
                utente.getEmail(),
                utente.getVia(),
                utente.getCap(),
                utente.getCitta(),
                utente.getProvincia(),
                utente.getDataNascita()
        );
    }
}
