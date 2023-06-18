package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.outputDTO.ListUnviewMessageDTO;
import com.application.virgo.exception.*;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface OffertaUtenteService {

    /**
     *  Metodo per salvare un'offerta fatta ad un utente
     * @param offerente dettagli dell'utente che ha proposto l'offerta
     * @param offertaProposta dettagli dell'offerta proposta da un utente
     * @param idVenditore id dell'utente che ha messo in vendita l'immobile
     * @return Un optional contenente l'offerta creata da un utente
     * @throws UtenteException nel caso in cui non trova l'utente proprietario
     */
    public Optional<OfferteUtente> saveOffertaToUtente(Utente offerente, Offerta offertaProposta, Long idVenditore)
            throws UtenteException, OffertaUtenteException;

    /**
     *  Metodo per salvare rilanciare un'offerta fatta da un altro utente
     * @param offerente dettagli dell'utente che ha proposto l'offerta
     * @param offertaProposta dettagli dell'offerta proposta da un utente
     * @param idVenditore id dell'utente che ha messo in vendita l'immobile
     * @return Un optional contenente l'offerta creata da un utente
     * @throws UtenteException nel caso in cui non trova l'utente proprietario
     */
    public Optional<OfferteUtente> rilanciaOffertaToUtente(Utente offerente, Offerta offertaProposta, Long idVenditore)
            throws UtenteException, OffertaUtenteException;
    /**
     * Permette di prelevare tutte le offerte ricevute dall'utente proprietario di un immobile
     * @param offerente l'utente autenticato che è proprietario dell'immobile
     * @param offset indice iniziale per la paginazione
     * @param pageSize dimensione della pagina
     * @return Una Page<> contenente la lista delle offerte che l'utente ha ricevuto
     * @throws UtenteException nel caso in cui l'utente autenticato non sia stato passato correttamente
     * @throws OffertaUtenteException nel caso in cui la grandezza della pagina superi quella impostata dal server
     */
    public List<OfferteUtente> getOfferteForUtenteProprietario(Utente offerente, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException;

    /**
     * Metodo per prelevare tutte le offerte scambiate tra due utenti dove l'utente autenticato è il proprietario
     * @param proprietario utente autenticato che sta accettando o rifiutando
     * @param offerente colui che ha proposto l'offerta
     * @param idImmobile immobile interessato nella contrattazione
     * @return La lista delle offerte ricevute da un utente
     */
    public List<OfferteUtente> allProposteBetweenUtenti(Utente proprietario, Utente offerente, Long idImmobile)
            throws UtenteException, ImmobileException;

    /**
     * Metodo per prelevare tutte le offerte scambiate tra due utenti dove l'utente autenticato è colui che ha inviato la richiesta
     * @param proprietario utente autenticato che sta accettando o rifiutando
     * @param offerente colui che ha proposto l'offerta
     * @param idImmobile immobile interessato nella contrattazione
     * @return La lista delle offerte ricevute da un utente
     */
    public List<OfferteUtente> allOfferteBetweenUtenti(Utente proprietario, Utente offerente, Long idImmobile)
            throws UtenteException, ImmobileException;

    /**
     * Metodo che permette di accettare un offerta
     * @param idOfferta Id dell'offerta da accettare
     * @param authUser utentte autenticato, proprietario dell'immobile, che ha accettato la richiesta
     * @return Il contratto formato tra due utenti
     * @throws OffertaException Se l'offerta non esiste
     * @throws OffertaUtenteException se la richiesta di offerta non esiste oppure se non è possibile accettare il contratto
     * @throws UtenteException se l'utente non è autenticato
     * @throws ImmobileException Se l'immobile non è stato trovato
     * @throws ContrattoException se non è stato possibile creare il contratto
     * @throws ContrattoUtenteException se non è stato possibile associare il contratto ai due utenti
     */
    public Optional<ContrattoUtente> acceptOfferta(Long idOfferta, Utente authUser)
            throws OffertaException, OffertaUtenteException, UtenteException, ImmobileException, ContrattoException, ContrattoUtenteException;

    /**
     * Metodo che permette di rifiutare un offerta
     * @param idOfferta Id dell'offerta da accettare
     * @param authUser utentte autenticato, proprietario dell'immobile, che ha accettato la richiesta
     * @return L'offerta tra i due utenti rifiutata
     * @throws OffertaException Se l'offerta non esiste
     * @throws OffertaUtenteException se la richiesta di offerta non esiste oppure se non è possibile accettare il contratto
     * @throws UtenteException se l'utente non è autenticato
     */
    public Optional<OfferteUtente> declineOfferta(Long idOfferta, Utente authUser)
            throws OffertaException, OffertaUtenteException, UtenteException;

    /**
     * Permette di prelevare tutte le offerte inviate dall'utente autenticato ad altri utenti
     * @param offerente l'utente autenticato che è proprietario dell'immobile
     * @param offset indice iniziale per la paginazione
     * @param pageSize dimensione della pagina
     * @return Una Page<> contenente la lista delle offerte che l'utente ha inviato
     * @throws UtenteException nel caso in cui l'utente autenticato non sia stato passato correttamente
     * @throws OffertaUtenteException nel caso in cui la grandezza della pagina superi quella impostata dal server
     */
    public Page<OfferteUtente> getOfferteProposte(Utente offerente, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException;


    /**
     * Restituisce una coppia mail-valore per sapere, per ogni mail, quanti sono i messaggi da visualizzare
     * @return Lista delle mail con il numero di messaggi da visualizzare
     */
    public List<ListUnviewMessageDTO> getListUnviewedMessaged();
}
