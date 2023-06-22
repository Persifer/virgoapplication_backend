package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.outputDTO.ContrattiUtenteDTO;
//import com.application.virgo.DTO.outputDTO.DettagliContrattoDTO;
import com.application.virgo.DTO.outputDTO.DettagliContrattoDTO;
import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Utente;

import java.util.List;
import java.util.Optional;

public interface ContrattoUtenteService {

    /**
     * Restituisce una lista di ContrattiUtenteDTO che rappresentano tutti i contratti stipulati dall'utente
     * per l'utente specificato, con offset e dimensione di pagina specificati.
     *
     * @param venditore     L'utente che ha messo in vendita l'immobile
     * @param offset        L'offset che indica l'indice iniziale da cui iniziare la pagina
     * @param pageSize      La dimensione di pagina per la paginazione.
     * @return La lista di contratti stipulati dall'utente
     * @throws UtenteException            se non è presente l'utente autenticato
     * @throws ContrattoUtenteException   se i numeri di offset e paginazione sono errati
     */
    public List<ContrattiUtenteDTO> getListaContrattiForUtente(Utente venditore, Long offset, Long pageSize)
            throws UtenteException, ContrattoUtenteException;

    /**
     * Salva un contratto tra un venditore e un acquirente specificati
     *
     * @param venditore             L'utente che ha messo in vendita l'immobile
     * @param acquirente            L'utente che ha proposto l'offerta per l'immobile
     * @param contrattoInteressato  Il contratto da associare tra due utenti
     * @return Un oggetto Optional contenente il ContrattoUtente, se salvato correttamente
     * @throws ContrattoUtenteException    se è impossibile reperire il contratto
     * @throws ContrattoException          se è impossibile reperire l'acquirente o il venditore
     */
    public Optional<ContrattoUtente> saveContrattoBetweenUtenti(Utente venditore, Utente acquirente, Contratto contrattoInteressato)
            throws ContrattoUtenteException, ContrattoException;

    /**
     * Permette di prelevare le informazioni di un contratto dato l'id del contratto interessato
     * @param authUSer Utente autenticato
     * @param idContratto id contratto interessato
     * @return DTO contente le informazioni utili al front-end
     * @throws UtenteException se l'utente non è autenticato
     * @throws ContrattoUtenteException se il contratto non esiste
     */
    public Optional<DettagliContrattoDTO> getDettagliContratto(Utente authUSer, Long idContratto) throws UtenteException, ContrattoUtenteException;
}
