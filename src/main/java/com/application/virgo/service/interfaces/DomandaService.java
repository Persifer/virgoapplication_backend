package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface DomandaService {

    public Optional<Domanda> addNewDomanda(DomandaDTO tempDomandaDTO, Utente authUser)
            throws UtenteException;
}
