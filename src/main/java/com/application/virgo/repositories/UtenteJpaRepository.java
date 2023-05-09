package com.application.virgo.repositories;

import com.application.virgo.model.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.crypto.spec.OAEPParameterSpec;
import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface UtenteJpaRepository extends JpaRepository<Utente, Long> {

    public Optional<Utente> getUtenteByEmailAndPassword(String email, String password);

    public  Optional<Utente> getUtenteByIdUtente(Long idUtente);

    public Optional<Utente> findByEmail(String username);
}
