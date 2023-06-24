/**
 * L'interfaccia UtenteService fornisce metodi per gestire la business logic per l'utente
 */
package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.ViewListaOfferteDTO;
import com.application.virgo.DTO.outputDTO.ViewOfferteBetweenUtentiDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Utente;

import java.util.List;
import java.util.Optional;


public interface UtenteService {

    /**
     * Ottiene l'utente corrispondente all'indirizzo email e alla password forniti
     *
     * @param username L'indirizzo email dell'utente
     * @param password La password dell'utente
     * @return Un oggetto Optional contenente l'Utente se presente
     */
    Optional<Utente> getUtenteByEmailAndPassword(String username, String password);

    /**
     * Ottiene le informazioni dell'utente, inserendole in un UtenteDTO, passando l'id dell'utente
     *
     * @param idUtenteToFound L'id dell'utente da trovare
     * @return Un oggetto Optional contenente l'UtenteDTO se presente
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database
     */
    Optional<UtenteDTO> getUtenteById(Long idUtenteToFound) throws UtenteException;

    /**
     * Ottiene l'utente corrispondente all'indirizzo email specificato
     *
     * @param idUtenteToFound L'indirizzo email dell'utente richiesto
     * @return Un oggetto Optional contenente l'Utente se trovato
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database
     */
    Optional<Utente> getUtenteClassByEmail(String idUtenteToFound) throws UtenteException;

    /**
     * Aggiorna le informazioni dell'utente che ha fatto la richiesta tramite id
     *
     * @param idUtente   L'id dell'utente da aggiornare
     * @param newUtente  L'oggetto UtenteDTO con le nuove informazioni dell'utente
     * @return Un oggetto Optional contenente l'Utente se l'aggiornamento è andata a buon fine
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database
     */
    Optional<Utente> updateUtenteInfoById(Long idUtente, UtenteDTO newUtente) throws UtenteException;

    /**
     * Permette di gestire la registrazione di un nuovo utente
     *
     * @param newUtente L'oggetto UtenteDTO con le informazioni del nuovo utente
     * @return Un oggetto Optional contenente l'Utente se la registrazione è avvenuta correttamente
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database oppure se
     *          un utente registrato presenta la mail che sta venendo usata per registrarsi
     */
    Optional<Utente> tryRegistrationHandler(UtenteDTO newUtente) throws UtenteException;

    /**
     * Metodo per l'aggiunta di una domanda ad un utente, permette di tenere traccia di quali domande ha fatto l'utente
     *
     * @param authUser      L'utente autenticato
     * @param domandaToAdd  La domanda da aggiungere
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database
     */
    void addDomandaToUtente(Utente authUser, Domanda domandaToAdd) throws UtenteException;

    /**
     * Metodo interno, quindi viene richiamato da altri metodi per reperire le informazioni necessarie, che permette di
     * prelevare le informazioni di un utente dal database
     *
     * @param idProprietario L'id dell'utente proprietario
     * @return Un oggetto Optional contenente l'Utente se trovato
     * @throws UtenteException se l'utente non esiste oppure se non è stato possibile reperirlo dal database
     */
    Optional<Utente> getUtenteClassById(Long idProprietario) throws UtenteException;

    /**
     * Ottiene una lista di idUtente che rappresentano gli utenti con cui, il venditore, ha un contratto
     *
     * @param proprietario L'utente proprietario dell'immobile
     * @param offset       L'offset di partenza per la paginazione
     * @param pageSize     La dimensione della pagina per la paginazione
     * @return Una lista di ListUtentiForProposteDTO contenente tutti gli idUtente dei possibili acquirenti
     * @throws OffertaUtenteException se non è stato possibile reperire l'offerta
     * @throws UtenteException        se l'utente non è autenticato oppure se non è stato possibile reperirlo dal database
     */
    List<ViewListaOfferteDTO> getListaProposte(Utente proprietario)
            throws OffertaUtenteException, UtenteException, ImmobileException;

    /**
     * Ottiene la lista delle offerte che l'utente ha proposto verso altri utenti
     *
     * @param proprietario L'utente che ha proposto le offerte
     * @param offset       L'offset di partenza per la paginazione
     * @param pageSize     La dimensione della pagina per la paginazione
     * @return Una lista di ViewListaOfferteDTO con le offerte fatte dall'utente
     * @throws OffertaUtenteException se non è stato possibile reperire l'offerta
     * @throws UtenteException        se l'utente non è autenticato oppure se non è stato possibile reperirlo dal database
     */
    List<ViewListaOfferteDTO> getListaOfferte(Utente proprietario)
            throws OffertaUtenteException, UtenteException;

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
    List<ViewOfferteBetweenUtentiDTO> getAllProposteBetweenUtenti(Utente proprietario, Long idOfferente, Long idImmobile)
            throws UtenteException, ImmobileException;

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
    List<ViewOfferteBetweenUtentiDTO> getAllOfferteBetweenUtenti(Utente proprietario, Long idOfferente, Long idImmobile)
            throws UtenteException, ImmobileException;

}
