package com.application.virgo.repositories;

import com.application.virgo.model.Immobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImmobileJpaRepository extends JpaRepository<Immobile, Long> {

    @Query("SELECT immobile FROM Immobile immobile WHERE immobile.idImmobile = :requestId AND immobile.isEnabled = true")
    public Optional<Immobile> getImmobilesByIdImmobile(@Param("requestId") Long idImmobile);

    @Query("SELECT COUNT(immobile.idImmobile) FROM Immobile immobile WHERE immobile.isEnabled = true")
    public Long countByIdImmobile();
}
