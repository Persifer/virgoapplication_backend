package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.LoginUtenteDTO;

import com.application.virgo.DTO.Mapper.UtenteMapper;
import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Ruolo;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.RuoloJpaRepository;
import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.interfaces.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import static com.application.virgo.utilities.Constants.*;

/* La classe UserService è la classe che permette il controller di interfacciarsi
* con il database. Tramite i suoi metodi permette una gestione controllata e corretta
* di tutte le operazioni possibili che un utente può fare
* */
@Service
@Transactional
@AllArgsConstructor
public class UtenteServiceImpl implements UtenteService {

    // Preleva in modo automatico l'istanza della classe UtenteJpaRepository per gestire la comunicazione
    // tra database e classe java

    private UtenteJpaRepository utenteRepo;
    private PasswordEncoder passwordEncoder;
    private UtenteMapper mapperUtente;
    private RuoloJpaRepository ruoloRepo;

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
            throw new UtenteException("L'utente selezionato non esiste, se ne inserica un altro");
        }

    }

    // fa l'update delle informazioni di un utente identificato tramite id
    @Override
    public Optional<Utente> updateUtenteInfoById(Long idUtente, UtenteDTO updatedUtenteDto) throws UtenteException{
        /*Optional<Utente> tempUtente = utenteRepo.findById(idUtente);
        if(tempUtente.isPresent()){

            Utente utenteToUpdate = tempUtente.get();
            utenteToUpdate = mapperUtente.apply(updatedUtenteDto);

                utenteRepo.save(updatedUtente);
                return Optional.of(updatedUtente);
            }else{
                throw new UtenteException("Errore nella modifica dei dati dell'utente");
            }
        }else{
            throw new UtenteException("L'utente che si vuole modificare non esiste");
        }*/

        return Optional.empty();
    }


    // Permette di registrare un nuovo utente
    public Optional<Utente> tryRegistrationHandler(UtenteDTO tempNewUtente) throws UtenteException {
        Utente newUtente = mapperUtente.apply(tempNewUtente);

        // Controllo che la data di nascita inserita correttamente
        if(newUtente.getDataNascita().isBefore(LocalDate.now())){
            // codifico la password tramtie l'encoder
            newUtente.setPassword(passwordEncoder.encode(newUtente.getPassword()));
            // seleziono il ruolo
            Optional<Ruolo> tempRuolo = ruoloRepo.getRuoloByRuolo(USER_ROLE);
            // controllo se il ruolo è presente
            if(tempRuolo.isPresent()){
                //  setto il ruolo dell'utente
                newUtente.setUserRole(Set.of(tempRuolo.get()));
                newUtente.setDomandeUtente(Set.of());

                return Optional.of(utenteRepo.save(newUtente));
            }else{
                throw new UtenteException("Ruolo non trovato");
            }

        }else{
            throw new UtenteException("La data di nascita deve essere minore di quella inserita!");
        }

    }

    @Override
    public void addDomandaToUtente(Utente authUser, Domanda domandaToAdd) throws UtenteException {
        if(authUser != null){
            // aggiungo la domanda all'utente
            authUser.getDomandeUtente().add(domandaToAdd);
            utenteRepo.save(authUser);
        }else{
            throw new UtenteException("Bisogna essere loggati per poter pubblicare una domanda");
        }
    }

}
