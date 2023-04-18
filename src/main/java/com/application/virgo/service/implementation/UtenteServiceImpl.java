package com.application.virgo.service.implementation;

import com.application.virgo.controller.DTO.LoginUtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.interfaces.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/* La classe UserService è la classe che permette il controller di interfacciarsi
* con il database. Tramite i suoi metodi permette una gestione controllata e corretta
* di tutte le operazioni possibili che un utente può fare
* */
@Service
public class UtenteServiceImpl implements UtenteService {

    // Preleva in modo automatico l'istanza della classe UtenteJpaRepository per gestire la comunicazione
    // tra database e classe java
    @Autowired
    private UtenteJpaRepository utenteRepo;

    // metodo che restituisce un utente tramite l'email e la password
    @Override
    public Optional<Utente> getUtenteByEmailAndPassword(String username, String password) {
        return Optional.empty();
    }

    // fa l'update delle informazioni di un utente identificato tramite id
    @Override
    public Optional<Utente> updateUtenteInfoById(Long idUtente, Utente newUtente) {
        return Optional.empty();
    }

    // permette di registrare un nuovo utente all'interno del database
    @Override
    public Optional<Utente> registrationHandler(Utente newUtente) throws UtenteException {
        return Optional.of(utenteRepo.save(newUtente));
    }

    // permette ad un utente registrato di eseguire il login
    @Override
    public Optional<Utente> loginHandler(LoginUtenteDTO tempUtente) throws UtenteException{
        return utenteRepo.getUtenteByEmailAndPassword(tempUtente.getEmail(), tempUtente.getPassword());
    }
}
