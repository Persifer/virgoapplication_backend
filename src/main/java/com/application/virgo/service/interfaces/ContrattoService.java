package com.application.virgo.service.interfaces;


import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Immobile;

import java.util.Optional;

public interface ContrattoService {

    public Optional<Contratto> createNewContratto(Immobile immobileInteressato, Float prezzoFinale) throws ImmobileException;
}
