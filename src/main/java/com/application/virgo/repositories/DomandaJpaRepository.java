package com.application.virgo.repositories;

import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomandaJpaRepository extends JpaRepository<Domanda, Long> {

    public Optional<Domanda> findByIdDomanda(Long id);

    public Page<Domanda> findByProprietarioDomanda(Utente proprietario, Pageable pageable);
}
