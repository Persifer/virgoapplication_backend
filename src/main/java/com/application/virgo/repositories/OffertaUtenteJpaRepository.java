package com.application.virgo.repositories;

import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OffertaUtenteJpaRepository extends JpaRepository<OfferteUtente, Long> {
}
