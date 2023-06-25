package com.application.virgo.repositories;

import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomandaJpaRepository extends JpaRepository<Domanda, Long> {

    @Query("SELECT domanda FROM Domanda domanda WHERE domanda.idDomanda = :id AND domanda.isEnabled = true")
    public Optional<Domanda> findByIdDomanda(Long id);

    @Query
    public Optional<Domanda> getByIdDomanda(Long id);

    public Page<Domanda> findByProprietarioDomanda(Utente proprietario, Pageable pageable);
}
