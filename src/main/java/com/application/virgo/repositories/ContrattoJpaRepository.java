package com.application.virgo.repositories;

import com.application.virgo.model.Contratto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
/**
 *
 */
public interface ContrattoJpaRepository extends JpaRepository<Contratto, Long> {
}
