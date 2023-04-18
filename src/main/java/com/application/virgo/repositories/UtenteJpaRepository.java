package com.application.virgo.repositories;

import com.application.virgo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;

public interface UtenteJpaRepository extends JpaRepository<Utente, Long> {

    public Optional<Utente> getUtenteByEmailAndPassword(String email, String password);
}
