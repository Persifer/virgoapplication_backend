package com.application.virgo.repositories;

import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContrattoUtenteJpaRepository extends JpaRepository<ContrattoUtente, ContrattoUtenteCompoundKey> {

    public Page<ContrattoUtente> getContrattoUtenteByVenditore(Pageable page, Utente venditore);

    public Long countByVenditore(Utente venditore);
}
