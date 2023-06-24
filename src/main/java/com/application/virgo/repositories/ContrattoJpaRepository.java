package com.application.virgo.repositories;

import com.application.virgo.model.Contratto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ContrattoJpaRepository extends JpaRepository<Contratto, Long> {

    public Optional<Contratto> getContrattoByIdContratto(Long idContratto);
}
