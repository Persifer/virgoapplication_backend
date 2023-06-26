package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.DTO.outputDTO.GetUtenteImmobiliDTO;
import com.application.virgo.DTO.outputDTO.HomeImmobileDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;

import java.util.List;
import java.util.Optional;

public interface ImmobileService {

// =========================================== HOME PAGE ===============================================================

    /**
     * Crea un nuovo immobile con le informazioni fornite, associandolo al proprietario specificato
     *
     * @param tempNewImmobile L'oggetto ImmobileDTO con le informazioni del nuovo immobile
     * @param proprietario    Il proprietario dell'immobile
     * @return Un oggetto Optional contenente l'ImmobileDTO che rappresenta i dati dell'immobile salvato
     * @throws ImmobileException se non è possibile creare l'immobile
     * @throws UtenteException   se non esiste l'utente autenticato
     */
    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile, Utente proprietario)
            throws ImmobileException, UtenteException;

    /**
     * Permette di prelevare le informazioni di un immobile quando lo richiede la stampa contratto
     * @param idImmobile  id immobile da prelevare
     * @return l'immobile selezionato
     * @throws ImmobileException se l'immobile non esiste
     */
    public Optional<Immobile> getImmobileInfoForContratto(Long idImmobile) throws ImmobileException;


    /**
     * Ottiene le informazioni di un immobile dato l'id, è un metodo che viene usato "internamente" cioè da altri metodi
     * e non da metodi che vengono richiamati dall'utente
     *
     * @param idImmobile L'ID dell'immobile
     * @return Un oggetto Optional contenente l'Immobile se presente
     * @throws ImmobileException se non è possibile trovare l'immobile
     */
    public Optional<Immobile> getImmobileInternalInformationById(Long idImmobile) throws ImmobileException;

    /**
     * Ottiene le informazioni dell'immobile con l'ID specificato inserendole all'interno di un DTO che serve, unicamente,
     * per la rappresentazione nel front-end
     *
     * @param idImmobile L'ID dell'immobile
     * @return Un oggetto Optional contenente il GetImmobileInfoDTO se presente
     * @throws ImmobileException se non è possibile trovare l'immobile
     */
    public Optional<GetImmobileInfoDTO> getImmobileById(Long idImmobile) throws ImmobileException;

    /**
     * Ottiene le informazioni dell'immobile con l'ID specificato dove l'utente che ha fatto la richiesta è
     * il proprietario dell'immobile
     *
     * @param idImmobile L'ID dell'immobile
     * @return Un oggetto Optional contenente il GetImmobileInfoDTO se presente
     * @throws ImmobileException se non è possibile trovare l'immobile
     */
    public Optional<GetImmobileInfoDTO> getImmobileByIdAsProprietario(Utente authUser, Long idImmobile) throws ImmobileException;



    /**
     * Ottiene una lista di immobili paginati per la pagina iniziale
     *
     * @param indiceIniziale L'indice di partenza per la paginazione
     * @param pageSize      La dimensione di pagina per la paginazione
     * @return Una lista di HomeImmobileDTO per la visualizzazione nella home page
     * @throws ImmobileException se si verifica un'eccezione relativa all'immobile
     */
    public List<HomeImmobileDTO> getAllImmobiliPaginated(Long idUtente, Long indiceIniziale, Long pageSize) throws ImmobileException;

    /**
     * Permette di eliminare un immobile
     * @param idImmobile immobile da eliminare
     * @param utente utente proprietario immobile
     * @return numero righe affette
     */
    public int immobileToDisable(Long idImmobile, Utente utente);


    /**
     * Aggiorna le informazioni interne dell'immobile specificato, essendo "interno" viene richiamato da un metodo
     * che, per lui, ha già fatto tutti i controlli
     *
     * @param immobileToUpdate L'immobile da aggiornare
     * @return True se l'aggiornamento è avvenuto correttamente, False altrimenti
     */
    public Boolean updateImmobileAfterAcceptance(Long immobileToUpdate) throws ImmobileException;

    // =====================================================================================================================

    // =============================================== FOR UTENTE ==========================================================

    /**
     * Ottiene una lista di immobili paginati per l'utente loggato
     *
     * @param indiceIniziale L'indice di partenza per la paginazione
     * @param pageSize      La dimensione di pagina per la paginazione
     * @param proprietario  L'utente proprietario degli immobili
     * @return Una lista di GetUtenteImmobiliDTO da inserire nella pagina di visualizzazione immobili di un utente
     * @throws ImmobileException se non è stato possibile trovare gli immobili
     * @throws UtenteException   se l'utente autenticato non esiste
     */
    public List<GetUtenteImmobiliDTO> getUtenteListaImmobili(Long indiceIniziale, Long pageSize, Utente proprietario)
            throws ImmobileException, UtenteException;


    public String getTitoloImmboileById(Long idImmobile) throws ImmobileException;
}
