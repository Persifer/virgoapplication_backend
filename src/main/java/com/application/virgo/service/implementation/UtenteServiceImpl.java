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
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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

    /**
     * Ottiene le informazioni dell'utente, inserendole in un UtenteDTO, passando l'id dell'utente
     *
     * @param idUtenteToFound L'id dell'utente da trovare
     * @return Un oggetto Optional contenente l'UtenteDTO se presente
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database
     */
    @Override
    public Optional<UtenteDTO> getUtenteById(Long idUtenteToFound) throws UtenteException{
        Optional<Utente> tempUtente = utenteRepo.getUtenteByIdUtente(idUtenteToFound);
        if(tempUtente.isPresent()){
            return Optional.of(mapperUtente.apply(tempUtente.get()));
        }else{
            throw new UtenteException("Utente non trovato!");
        }

    }

    /**
     * Ottiene l'utente corrispondente all'indirizzo email specificato
     *
     * @param idUtenteToFound L'indirizzo email dell'utente richiesto
     * @return Un oggetto Optional contenente l'Utente se trovato
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database
     */
    public Optional<Utente> getUtenteClassById(Long idUtenteToFound) throws UtenteException{
        Optional<Utente> tempUtente = utenteRepo.getUtenteByIdUtente(idUtenteToFound);
        if(tempUtente.isPresent()){
            return tempUtente;
        }else{
            throw new UtenteException("L'utente selezionato non esiste, se ne inserica un altro");
        }

    }


    /**
     * Permette di creare una lista di utenti dati gli id per le propsote
     * @param listOfferteUtente lista id utenti in offerte
     * @return lista di utenti
     * @throws UtenteException se non trova l'utente
     */
    private List<ListUtentiForProposteDTO> createListaUtentiForProposte(List<Long> listOfferteUtente) throws UtenteException {
        List<ListUtentiForProposteDTO> listUtenti = new ArrayList<>();
        for(Long idUtente : listOfferteUtente){
            Optional<Utente> tempUtente = getUtenteClassById(idUtente);
            tempUtente.ifPresent(utente -> listUtenti.add(listUtentiForProposteMapper.apply(utente)));
        }

        return listUtenti;
    }

    /**
     * Permette di avere la lista delle offerte ricevute da un determinato utente
     *
     * @param proprietario istanze della classe utente che rappresenta il proprietario
     * @return Ritorna la lista di offerte di un utente dove lui è il proprietario
     * @throws OffertaUtenteException quando non trova una pagina
     * @throws UtenteException        quando l'utente non è autenticato
     */
    @Override
    public List<ViewListaOfferteDTO> getListaProposte(Utente proprietario)
            throws OffertaUtenteException, UtenteException, ImmobileException {

        List<ViewListaOfferteDTO> listOfferteUtente =
                offerteUtenteService.getOfferteForUtenteProprietario(proprietario);

        if(!listOfferteUtente.isEmpty()){
            return listOfferteUtente;
        }
        return List.of();
    }



    /**
     * Permette di avere la lista di tutte le offerte fatte dal proprietario dell'account
     * @param offerente colui che ha fatto le offerte
     * @return Lista delle offerte prese dal database
     * @throws OffertaUtenteException se non trova le offerte
     * @throws UtenteException se l'utente non è autenticato
     */
    @Override
    public List<ViewListaOfferteDTO> getListaOfferte(Utente offerente)
            throws OffertaUtenteException, UtenteException, ImmobileException {

        return offerteUtenteService.getOfferteProposte(offerente);
    }

    /**
     * Ottiene una lista di proposte tra l'utente proprietario, l'offerente specificato e l'immobile specificato
     *
     * @param proprietario L'utente proprietario delle proposte
     * @param idOfferente  L'ID dell'offerente
     * @param idImmobile   L'ID dell'immobile
     * @return Una lista di ViewOfferteBetweenUtentiDTO
     * @throws UtenteException   se si verifica un'eccezione relativa all'utente
     * @throws ImmobileException se si verifica un'eccezione relativa all'immobile
     */
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

    /**
     * Ottiene una lista di offerte tra l'utente proprietario, l'offerente specificato e l'immobile specificato
     *
     * @param proprietario L'utente proprietario delle offerte
     * @param idOfferente  L'ID dell'offerente
     * @param idImmobile   L'ID dell'immobile
     * @return Una lista di ViewOfferteBetweenUtentiDTO
     * @throws UtenteException   se si verifica un'eccezione relativa all'utente
     * @throws ImmobileException se si verifica un'eccezione relativa all'immobile
     */
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
     * Permette di gestire la registrazione di un nuovo utente
     *
     * @param tempNewUtente L'oggetto UtenteDTO con le informazioni del nuovo utente
     * @return Un oggetto Optional contenente l'Utente se la registrazione è avvenuta correttamente
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database oppure se
     *          un utente registrato presenta la mail che sta venendo usata per registrarsi
     */
    public Optional<Utente> tryRegistrationHandler(UtenteDTO tempNewUtente) throws UtenteException {

        Optional<Utente> checkedUtente = utenteRepo.getUtenteByEmail(tempNewUtente.getEmail());
        if(checkedUtente.isEmpty()){
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

                    newUtente.setIsAccountNonExpired(Boolean.TRUE);
                    newUtente.setIsAccountNonLocked(Boolean.TRUE);
                    newUtente.setIsCredentialsNonExpired(Boolean.TRUE);
                    newUtente.setIsEnabled(Boolean.TRUE);

                    return Optional.of(utenteRepo.save(newUtente));
                }else{
                    throw new UtenteException("Ruolo non trovato");
                }

            }else{
                throw new UtenteException("La data di nascita deve essere minore di quella inserita!");
            }
        }else{
            throw new UtenteException("L'utente già esiste all'interno del database");
        }


    }


    /**
     * Metodo per l'aggiunta di una domanda ad un utente, permette di tenere traccia di quali domande ha fatto l'utente
     *
     * @param authUser      L'utente autenticato
     * @param domandaToAdd  La domanda da aggiungere
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database
     */
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
