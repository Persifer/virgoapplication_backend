package com.application.virgo.repositories;

import com.application.virgo.model.Immobile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImmobileJpaRepository extends JpaRepository<Immobile, Long> {

    public Optional<Immobile> getImmobilesByIdImmobile(Long idImmobile);

    @Query("SELECT COUNT(immobile.idImmobile) FROM Immobile immobile")
    public Long countByIdImmobile();
}
