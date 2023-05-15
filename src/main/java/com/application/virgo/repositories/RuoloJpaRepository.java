package com.application.virgo.repositories;

import com.application.virgo.model.Ruolo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface RuoloJpaRepository extends JpaRepository<Ruolo, Long> {

    public Optional<Ruolo> getRuoloByRuolo(String ruolo);
}
