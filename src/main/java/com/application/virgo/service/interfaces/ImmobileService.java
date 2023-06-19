package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.DTO.outputDTO.GetUtenteImmobiliDTO;
import com.application.virgo.DTO.outputDTO.HomeImmobileDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface ImmobileService {

// =========================================== HOME PAGE ===============================================================

    /**
     * Crea un nuovo immobile con le informazioni fornite, associandolo al proprietario specificato
     *
     * @param tempNewImmobile L'oggetto ImmobileDTO con le informazioni del nuovo immobile
     * @param proprietario    Il proprietario dell'immobile
     * @param uploadedFile    I file caricati relativi all'immobile
     * @return Un oggetto Optional contenente l'ImmobileDTO che rappresenta i dati dell'immobile salvato
     * @throws ImmobileException se non è possibile creare l'immobile
     * @throws UtenteException   se non esiste l'utente autenticato
     */
    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile, Utente proprietario, MultipartFile[] uploadedFile)
            throws ImmobileException, UtenteException;

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
     * Ottiene i dati dell'immobile con l'ID specificato per l'aggiornamento delle informazioni
     *
     * @param idImmobile     L'ID dell'immobile
     * @param proprietario   Il proprietario dell'immobile
     * @return Un oggetto Optional contenente l'ImmobileDTO se presente
     * @throws ImmobileException se non è possibile trovare l'immobile
     * @throws UtenteException   se non è possibile trovare l'utente autenticato
     */
    public Optional<ImmobileDTO> getImmobileByIdToUpdate(Long idImmobile, Utente proprietario)
            throws ImmobileException, UtenteException;

    /**
     * Aggiorna le informazioni dell'immobile specificato
     *
     * @param tempUpdatedImmobile   L'oggetto ImmobileDTO con le informazioni aggiornate
     * @param proprietario          Il proprietario dell'immobile
     * @param idImmobileToUpdate    L'ID dell'immobile da aggiornare
     * @return Un oggetto Optional contenente l'Immobile se l'aggiornamento è avvenuto con successo
     * @throws ImmobileException se non è possibile trovare l'immobile
     * @throws UtenteException   se non è possibile trovare l'utente autenticato
     */
    public Optional<Immobile> updateImmobileInformation(ImmobileDTO tempUpdatedImmobile, Utente proprietario, Long idImmobileToUpdate)
            throws ImmobileException, UtenteException;

    /**
     * Ottiene una lista di immobili paginati per la pagina iniziale
     *
     * @param indiceIniziale L'indice di partenza per la paginazione
     * @param pageSize      La dimensione di pagina per la paginazione
     * @return Una lista di HomeImmobileDTO per la visualizzazione nella home page
     * @throws ImmobileException se si verifica un'eccezione relativa all'immobile
     */
    public List<HomeImmobileDTO> getAllImmobiliPaginated(Long indiceIniziale, Long pageSize) throws ImmobileException;

    /**
     * Ottiene una lista di immobili filtrati in base ad un filtro specifiato dall'utente
     *
     * @param filter Il filtro per la ricerca degli immobili
     * @return Una lista di GetImmobileInfoDTO filtrati
     */
    public List<GetImmobileInfoDTO> getFilteredImmobiliPaginated(String filter);

    /**
     * Ottiene una lista di immobili in base alla parola chiave specificata
     *
     * @param keyword La parola chiave per la ricerca degli immobili
     * @return Una lista di GetImmobileInfoDTO
     */
    public List<GetImmobileInfoDTO> getImmobiliByKeyword(String keyword);

    /**
     * Aggiorna le informazioni interne dell'immobile specificato, essendo "interno" viene richiamato da un metodo
     * che, per lui, ha già fatto tutti i controlli
     *
     * @param immobileToUpdate L'immobile da aggiornare
     * @return True se l'aggiornamento è avvenuto correttamente, False altrimenti
     */
    public Boolean internalImmobileUpdate(Immobile immobileToUpdate);

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

    /**
     * Permette di aggiungere una domanda ad un immobile
     *
     * @param Domanda domanda da aggiungere all'immobile
     * @param authUser utente autenticato che aggiunge la domanda
     * @param idImmobileInteressato id immobile a cui aggiungere la domanda
     * @return L'immobile con la domanda aggiunta
     * @throws ImmobileException se l'immobile non è stato trovato
     * @throws UtenteException se l'utente autenticato non esiste
     */
    public Optional<Immobile> addNewDomandaToImmobile(Domanda Domanda, Utente authUser, Long idImmobileInteressato)
            throws ImmobileException, UtenteException;

}
