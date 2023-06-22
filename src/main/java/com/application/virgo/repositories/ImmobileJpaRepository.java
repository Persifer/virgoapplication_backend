package com.application.virgo.repositories;

import com.application.virgo.model.Immobile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImmobileJpaRepository extends JpaRepository<Immobile, Long> {

    @Query("SELECT immobile FROM Immobile immobile JOIN Domanda ON (immobile.idImmobile = Domanda.idDomanda) WHERE immobile.idImmobile = :requestId AND immobile.isEnabled = true")
    public Optional<Immobile> getImmobilesByIdImmobile(@Param("requestId") Long idImmobile);

    @Query("SELECT immobile FROM Immobile immobile " +
            "JOIN Utente utente ON (utente.idUtente = immobile.proprietario.idUtente)" +
            "WHERE utente.idUtente = :idUtente")
    public Page<Immobile> getUtenteImmobiliList(@Param("idUtente") Long idUtente, Pageable pageable);



    @Query("SELECT COUNT(immobile.idImmobile) FROM Immobile immobile WHERE immobile.isEnabled = true")
    public Long countByIdImmobile();
}
