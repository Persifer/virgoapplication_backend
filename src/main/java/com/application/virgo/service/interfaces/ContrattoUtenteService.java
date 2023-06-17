package com.application.virgo.service.interfaces;

import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Utente;

import java.util.Optional;

public interface ContrattoUtenteService {

    public Optional<ContrattoUtente> saveContrattoBetweenUtenti(Utente venditore, Utente acquirente, Contratto contrattoInteressato)
        throws ContrattoUtenteException, ContrattoException, ImmobileException;
}
