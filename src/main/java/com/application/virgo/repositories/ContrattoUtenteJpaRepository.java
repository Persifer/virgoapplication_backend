package com.application.virgo.repositories;

import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContrattoUtenteJpaRepository extends JpaRepository<ContrattoUtente, ContrattoUtenteCompoundKey> {
}
