package com.application.virgo.repositories;

import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImmobileJpaRepository extends JpaRepository<Immobile, Long> {

    @Query("SELECT immobile FROM Immobile immobile "+
            " WHERE immobile.idImmobile = :requestId AND immobile.isEnabled=true")
    public Optional<Immobile> getImmobilesByIdImmobile(@Param("requestId") Long idImmobile);

    @Query("SELECT immobile FROM Immobile immobile "+
            " WHERE immobile.idImmobile = :requestId")
    public Optional<Immobile> getImmobilesByIdImmobileAfterContratto(@Param("requestId") Long idImmobile);

    @Query("SELECT immobile FROM Immobile immobile "+
            " WHERE immobile.idImmobile = :requestId AND immobile.proprietario.idUtente = :idUtente" +
            " AND immobile.isEnabled=true")
    public Optional<Immobile> getImmobilesByIdImmobileAsProprietario(@Param("requestId") Long idImmobile,
                                                                     @Param("idUtente") Long idUtente );

    @Query("SELECT immobile FROM Immobile immobile " +
            "JOIN Utente utente ON (utente.idUtente = immobile.proprietario.idUtente)" +
            "WHERE utente.idUtente = :idUtente AND immobile.isEnabled = true")
    public Page<Immobile> getUtenteImmobiliList(@Param("idUtente") Long idUtente, Pageable pageable);

    @Query("SELECT domanda FROM Domanda domanda" +
            " JOIN Immobile immobile ON (domanda.immobileInteressato.idImmobile = immobile.idImmobile)" +
            "WHERE immobile.idImmobile = :idImmobile AND domanda.isEnabled = true")
    public List<Domanda> domandeImmobile(@Param("idImmobile") Long idImmobil);

    @Modifying
    @Query("UPDATE Immobile immobile SET immobile.isEnabled = false WHERE immobile.idImmobile = :idImmobile " +
            "AND immobile.proprietario.idUtente = :idUtente")
    public int disableImmobile(@Param("idImmobile") Long idImmobile, @Param("idUtente") Long idUtente);

    @Query("SELECT COUNT(immobile.idImmobile) FROM Immobile immobile WHERE immobile.isEnabled = true")
    public Long countByIdImmobile();

    @Query("SELECT immobile FROM Immobile immobile WHERE immobile.proprietario.idUtente <> :idUtente AND immobile.isEnabled = true ")
    public Page<Immobile> findAllByIsEnabledTrue(Pageable pageable, @Param("idUtente") Long idUtente);
}
