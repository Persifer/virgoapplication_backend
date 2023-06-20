package com.application.virgo.repositories;

import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContrattoUtenteJpaRepository extends JpaRepository<ContrattoUtente, ContrattoUtenteCompoundKey> {

    public Page<ContrattoUtente> getContrattoUtenteByVenditore(Pageable page, Utente venditore);

    public Long countByVenditore(Utente venditore);

    @Query("SELECT ContrattoUtente " +
            "FROM ContrattoUtente contratto " +
                "JOIN Contratto contract ON (contratto.contrattoInteressato.idContratto = contract.idContratto)" +
            "WHERE contract.idContratto = :idContratto")
    public Optional<ContrattoUtente> getContrattoUtenteByIdContratto(@Param("idContratto") Long idContratto);
}
