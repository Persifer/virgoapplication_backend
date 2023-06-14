package com.application.virgo.repositories;

import com.application.virgo.model.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OffertaJpaRepository extends JpaRepository<Offerta, Long> {

    public Optional<Offerta> getOffertaByIdOfferta(Long idOfferta);
}
