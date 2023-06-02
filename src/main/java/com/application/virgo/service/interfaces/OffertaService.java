package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Offerta;

import java.util.Optional;

public interface OffertaService {

    public Optional<Offerta> createNewOfferta(InsertOffertaDTO datiOfferta)
            throws OffertaException, ImmobileException;
}
