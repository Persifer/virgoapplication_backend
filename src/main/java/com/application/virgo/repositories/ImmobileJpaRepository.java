package com.application.virgo.repositories;

import com.application.virgo.model.Immobile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ImmobileJpaRepository extends JpaRepository<Immobile, Long> {

    public Optional<Immobile> getImmobilesByIdImmobile(Long idImmobile);

    public Long countByIdImmobile();
}
