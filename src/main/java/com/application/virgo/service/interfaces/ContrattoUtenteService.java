package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.outputDTO.ContrattiUtenteDTO;
import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Utente;

import java.util.List;
import java.util.Optional;

public interface ContrattoUtenteService {

    public List<ContrattiUtenteDTO> getListaContrattiForUtente(Utente venditore, Long offset, Long pageSize) throws UtenteException, ImmobileException, ContrattoUtenteException;

    public Optional<ContrattoUtente> saveContrattoBetweenUtenti(Utente venditore, Utente acquirente, Contratto contrattoInteressato)
        throws ContrattoUtenteException, ContrattoException, ImmobileException;
}
