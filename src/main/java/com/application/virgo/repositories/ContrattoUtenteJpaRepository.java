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

import java.util.List;
import java.util.Optional;

@Repository
public interface ContrattoUtenteJpaRepository extends JpaRepository<ContrattoUtente, ContrattoUtenteCompoundKey> {

    @Query(value = "select * from contratto_utente where (id_acquirente = :idUtente or id_venditore = :idUtente)",
            nativeQuery = true)
    public List<ContrattoUtente> findContrattiRelatedToUtente(@Param("idUtente") Long venditore);

    public Long countByVenditore(Utente venditore);

    @Query(value = "select * from contratto_utente where contratto_utente.id_contratto = :idContratto", nativeQuery = true)
    public Optional<ContrattoUtente> getContrattoUtenteByIdContratto(@Param("idContratto") Long idContratto);


    @Query(value = "select * from contratto_utente " +
            "where (contratto_utente.id_venditore = :idUtente or contratto_utente.id_acquirente = :idUtente) " +
            "and contratto_utente.id_contratto = :idContratto", nativeQuery = true)
    public Optional<ContrattoUtente> getContrattoUtenteByIdContrattoAndIdUtente(@Param("idContratto") Long idContratto,
                                                                                @Param("idUtente") Long idUtente);
}
