package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.LoginUtenteDTO;

import com.application.virgo.DTO.Mapper.UtenteMapper;
import com.application.virgo.DTO.inputDTO.UtenteDTO;
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

    @Autowired
    private UtenteMapper mapperUtente;

    // metodo che restituisce un utente tramite l'email e la password
    @Override
    public Optional<Utente> getUtenteByEmailAndPassword(String username, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<UtenteDTO> getUtenteById(Long idUtenteToFound) throws UtenteException{
        Optional<Utente> tempUtente = utenteRepo.getUtenteByIdUtente(idUtenteToFound);
        if(tempUtente.isPresent()){
            return Optional.of(mapperUtente.apply(tempUtente.get()));
        }else{
            throw new UtenteException("Utente non trovato!");
        }

    }

    public Optional<Utente> getUtenteClassById(Long idUtenteToFound) throws UtenteException{
        Optional<Utente> tempUtente = utenteRepo.getUtenteByIdUtente(idUtenteToFound);
        if(tempUtente.isPresent()){
            return tempUtente;
        }else{
            throw new UtenteException("Utente non trovato!");
        }

    }

    // fa l'update delle informazioni di un utente identificato tramite id
    @Override
    public Optional<Utente> updateUtenteInfoById(Long idUtente, UtenteDTO updatedUtenteDto) throws UtenteException{
        Optional<Utente> tempUtente = utenteRepo.findById(idUtente);
        if(tempUtente.isPresent()){
            Utente utenteToUpdate = tempUtente.get();
           Utente updatedUtente = null;
           if(updatedUtente != null){
               utenteRepo.save(updatedUtente);
               return Optional.of(updatedUtente);
           }else{
               throw new UtenteException("Errore nella modifica dei dati dell'utente");
           }
        }else{
            throw new UtenteException("L'utente che si vuole modificare non esiste");
        }
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
