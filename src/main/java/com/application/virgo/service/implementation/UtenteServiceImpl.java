package com.application.virgo.service.implementation;

import com.application.virgo.DTO.Mapper.ListUtentiForProposteMapper;
import com.application.virgo.DTO.Mapper.ViewListaOfferteMapper;
import com.application.virgo.DTO.Mapper.ViewOfferteBtwnUtentiMapper;

import com.application.virgo.DTO.Mapper.UtenteMapper;
import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.ListUtentiForProposteDTO;
import com.application.virgo.DTO.outputDTO.ViewListaOfferteDTO;
import com.application.virgo.DTO.outputDTO.ViewOfferteBetweenUtentiDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Ruolo;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.RuoloJpaRepository;
import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.interfaces.EmailSenderService;
import com.application.virgo.service.interfaces.OffertaUtenteService;
import com.application.virgo.service.interfaces.UtenteService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    private final UtenteJpaRepository utenteRepo;
    private final PasswordEncoder passwordEncoder;

    private final UtenteMapper mapperUtente;
    private final ViewListaOfferteMapper mapperOfferteUtente;
    private final ListUtentiForProposteMapper listUtentiForProposteMapper;
    private final  ViewOfferteBtwnUtentiMapper mapperOfferteBtwnUtenti;

    private final OffertaUtenteService offerteUtenteService;
    private final RuoloJpaRepository ruoloRepo;
    private final EmailSenderService emailService;

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

    /**
     * Permette di avere la lista delle offerte ricevute da un determinato utente
     * @param proprietario istanze della classe utente che rappresenta il proprietario
     * @param offset indice di inizio della paginazione
     * @param pageSize grandezza della pagina
     * @return Ritorna la lista di offerte di un utente dove lui è il proprietario
     * @throws OffertaUtenteException quando non trova una pagina
     * @throws UtenteException quando l'utente non è autenticato
     */
    @Override
    public List<ListUtentiForProposteDTO> getListaProposte(Utente proprietario, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException {

        List<Long> listOfferteUtente = offerteUtenteService.getOfferteForUtenteProprietario(proprietario, offset, pageSize);

        if(!listOfferteUtente.isEmpty()){
            // Converto la lista di id utenti in una lista di utenti effettiva
            List<Utente> listUtenti = listOfferteUtente.stream()
                    .map(elem -> {
                        try {
                            return getUtenteClassById(elem).get();
                        } catch (UtenteException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    })
                    .collect(Collectors.toList());
            //Utilizzo la lista di utenti per passare i dati al front-end
            return listUtenti.stream().map(listUtentiForProposteMapper).collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<ViewListaOfferteDTO> getListaOfferte(Utente offerente, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException {

        Page<OfferteUtente> listOfferteUtente = offerteUtenteService.getOfferteProposte(offerente, offset, pageSize);
        if(!listOfferteUtente.isEmpty()){
            return listOfferteUtente.stream().map(mapperOfferteUtente).collect(Collectors.toList());
        }
        return List.of();
    }

    @Override
    public List<ViewOfferteBetweenUtentiDTO> getAllProposteBetweenUtenti(Utente proprietario, Long idOfferente, Long idImmobile)
            throws UtenteException, ImmobileException {
        //Prelevo dal database le informazioni di un utente
        Optional<Utente> offerente = getUtenteClassById(idOfferente);
        if(offerente.isPresent()){
            //Prelevo la lista delle offerte tra due utenti
            List<OfferteUtente> listaOfferteTraUtenti = offerteUtenteService.allProposteBetweenUtenti(proprietario, offerente.get(), idImmobile);
            //se non vuota
            if(!listaOfferteTraUtenti.isEmpty()){
                //ritorno la lista di elementi
                return listaOfferteTraUtenti.stream().map(mapperOfferteBtwnUtenti).collect(Collectors.toList());
            }else{
                //altrimenti ritorno una lista vuota
                return List.of();
            }
        }else{
            throw new UtenteException("L'utente che ha proposto le offerte non è stato trovato");
        }
    }

    @Override
    public List<ViewOfferteBetweenUtentiDTO> getAllOfferteBetweenUtenti(Utente proprietario, Long idOfferente, Long idImmobile)
            throws UtenteException, ImmobileException {
        //Prelevo dal database le informazioni di un utente
        Optional<Utente> offerente = getUtenteClassById(idOfferente);
        if(offerente.isPresent()){
            //Prelevo la lista delle offerte tra due utenti
            List<OfferteUtente> listaOfferteTraUtenti = offerteUtenteService.allOfferteBetweenUtenti(proprietario, offerente.get(), idImmobile);
            //se non vuota
            if(!listaOfferteTraUtenti.isEmpty()){
                //ritorno la lista di elementi
                return listaOfferteTraUtenti.stream().map(mapperOfferteBtwnUtenti).collect(Collectors.toList());
            }else{
                //altrimenti ritorno una lista vuota
                return List.of();
            }
        }else{
            throw new UtenteException("L'utente che ha proposto le offerte non è stato trovato");
        }
    }

    public Optional<Utente> getUtenteClassByEmail(String emailUtenteToFound) throws UtenteException{
        Optional<Utente> tempUtente = utenteRepo.findByEmail(emailUtenteToFound);
        if(tempUtente.isPresent()){
            return tempUtente;
        }else{
            throw new UtenteException("L'utente selezionato non esiste, se ne inserica un altro");
        }

    }

    // fa l'update delle informazioni di un utente identificato tramite id

    /**
     * Permette di aggiornare le informazioni di un utente
     * @param idUtente prende l'id dell'utente di cui si vogliono aggiornare le informazioni
     * @param updatedUtenteDto Prende le informazioni aggiornate
     * @return L'utente aggiornato
     * @throws UtenteException se l'utente non è autenticato
     */
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

                Optional<Utente> registeredUtente = Optional.of(utenteRepo.save(newUtente));

                return registeredUtente;
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
