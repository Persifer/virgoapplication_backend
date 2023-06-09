package com.application.virgo.service.implementation;

import com.application.virgo.DTO.Mapper.ContrattiUtenteMapper;
import com.application.virgo.DTO.outputDTO.ContrattiUtenteDTO;
import com.application.virgo.DTO.outputDTO.DettagliContrattoDTO;
import com.application.virgo.exception.ContrattoException;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.ContrattoUtenteJpaRepository;
import com.application.virgo.service.interfaces.ContrattoService;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.utilities.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.application.virgo.utilities.Constants.FORMATTER;

@Service
@Transactional
@AllArgsConstructor
public class ContrattoUtenteServiceImpl implements ContrattoUtenteService {

    private final ContrattoUtenteJpaRepository contrattoUtenteRepo;

    private final ContrattiUtenteMapper contrattiUtenteMapper;

    private final ContrattoService contrattoService;


    /**
     * Restituisce una lista di ContrattiUtenteDTO che rappresentano tutti i contratti stipulati dall'utente
     * per l'utente specificato, con offset e dimensione di pagina specificati.
     *
     * @param venditore     L'utente che ha messo in vendita l'immobile
     * @param inidiceIniziale        L'offset che indica l'indice iniziale da cui iniziare la pagina
     * @param pageSize      La dimensione di pagina per la paginazione.
     * @return La lista di contratti stipulati dall'utente
     * @throws UtenteException            se non è presente l'utente autenticato
     * @throws ContrattoUtenteException   se i numeri di offset e paginazione sono errati
     */
    @Override
    public List<ContrattiUtenteDTO> getListaContrattiForUtente(Utente venditore, Long inidiceIniziale, Long pageSize)
            throws UtenteException, ContrattoUtenteException, ContrattoException {
        List<ContrattiUtenteDTO> result = new ArrayList<>();
        // controllo che il venditore passato sia presente
        if(venditore != null){
            // se l'indice iniziale non supera il limite impsoto
            if(inidiceIniziale < pageSize - contrattoUtenteRepo.countByVenditore(venditore) ){
                // se la page non supera il limite imposto
                if(pageSize < Constants.PAGE_SIZE){

                    // prelevo e metto in una Page<ContrattoUtente> tutti i contratti legati ad un utente
                    List<ContrattoUtente> listContratti = contrattoUtenteRepo.findContrattiRelatedToUtente(
                            venditore.getIdUtente()
                            );

                    if(!listContratti.isEmpty()){
                        // se non è vuota estraggo tutti i dati dei contratti per singolo contratto_utente
                        for(ContrattoUtente contrattoUtente : listContratti){
                            Optional<Contratto> contract =
                                    contrattoService.getContrattoById(contrattoUtente.getContrattoInteressato().getIdContratto());
                            if (contract.isPresent()){
                                contrattoUtente.setContrattoInteressato(contract.get());
                                result.add(contrattiUtenteMapper.apply(contrattoUtente));
                            }
                        }
                    }

                    // converte con la stream una page di ContrattoUtente in una lista di ContrattiUtenteDTO
                    return result;
                }else {
                    throw new ContrattoUtenteException("2 - Attenzione il numero dell'elemento da cui partire è troppo alto " + pageSize);
                }
            }else{
                throw new ContrattoUtenteException("1 -Attenzione il numero dell'elemento da cui partire è troppo alto");
            }
        }else{
            throw new UtenteException("Impossibile trovare l'utente autenticato");
        }
    }

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
    @Override
    public Optional<ContrattoUtente> saveContrattoBetweenUtenti(Utente venditore, Utente acquirente, Contratto contrattoInteressato)
            throws ContrattoUtenteException, ContrattoException {
        // controllo che il venditore passato sia presente
        if(venditore != null){
            // controllo che l'acquirente passato sia presente
            if(acquirente != null){
                // controllo che il contratto passato sia presente
                if(contrattoInteressato!=null){

                    // creo l'associazione tra acquirente e venditore tramite ContrattoUtente

                    ContrattoUtente newContrattoUtente = new ContrattoUtente();

                    newContrattoUtente.setContrattoInteressato(contrattoInteressato);
                    newContrattoUtente.setAcquirente(acquirente);
                    newContrattoUtente.setVenditore(venditore);

                    newContrattoUtente.setIdContrattoUtente(
                            new ContrattoUtenteCompoundKey(
                                    venditore.getIdUtente(),
                                    acquirente.getIdUtente(),
                                    contrattoInteressato.getIdContratto())
                    );

                    return Optional.of(contrattoUtenteRepo.save(newContrattoUtente));
                }else{
                    throw new ContrattoException("Impossibile reperire il contratto da assegnare");
                }
            }else{
                throw new ContrattoUtenteException("Impossibile reperire l'acquirente per creare il contratto");
            }
        }else{
            throw new ContrattoUtenteException("Impossibile reperire il venditore per creare il contratto");
        }

    }

    /**
     * Permette di prelevare le informazioni di un contratto dato l'id del contratto interessato
     * @param authUser Utente autenticato
     * @param idContratto id contratto interessato
     * @return DTO contente le informazioni utili al front-end
     * @throws UtenteException se l'utente non è autenticato
     * @throws ContrattoUtenteException se il contratto non esiste
     */
    @Override
    public Optional<DettagliContrattoDTO> getDettagliContratto(Utente authUser, Long idContratto)
            throws UtenteException, ContrattoUtenteException, ContrattoException {
        if(authUser !=  null){
            //prelevo il contratto dal database
            Optional<ContrattoUtente> tempRequestedContrattoUtente = contrattoUtenteRepo.getContrattoUtenteByIdContratto(idContratto);
            Optional<Contratto> tempWantedContratto;

            // controllo se il contratto esiste veramente
            if(tempRequestedContrattoUtente.isPresent()){
                // lo prelevo dall'optional
                ContrattoUtente requestedContrattoUtente = tempRequestedContrattoUtente.get();
                tempWantedContratto = contrattoService.getContrattoById(
                        requestedContrattoUtente.getIdContrattoUtente().getIdContratto());
               if(tempWantedContratto.isPresent()){
                   Contratto wantedContratto = tempWantedContratto.get();
                   // controllo che l'utente che ha fatto la richiesta sia o il venditore o l'acquirente, altrimenti lancio errore
                   if(authUser.getIdUtente().equals(requestedContrattoUtente.getAcquirente().getIdUtente()) ||
                           authUser.getIdUtente().equals(requestedContrattoUtente.getVenditore().getIdUtente())){

                       // ====================================== CREAZIONE IMMOBILE DTO PER IL PASSAGGIO DELLE INFORMAZIONI =======================================
                       DettagliContrattoDTO dettagliContrattoDTO = new DettagliContrattoDTO();

                       dettagliContrattoDTO.setPrezzoDelContratto(wantedContratto.getPrezzo());
                       dettagliContrattoDTO.setDataStipulazioneContratto(
                               FORMATTER.format(wantedContratto.getDataStipulazione()));

                       dettagliContrattoDTO.setTitoloImmobile(wantedContratto.getImmobileInteressato().getTitolo());
                       dettagliContrattoDTO.setIdImmobile(wantedContratto.getImmobileInteressato().getIdImmobile());
                       dettagliContrattoDTO.setIdContratto(wantedContratto.getIdContratto());

                       dettagliContrattoDTO.setIdContratto(wantedContratto.getIdContratto());

                       if(authUser.getIdUtente().equals(requestedContrattoUtente.getAcquirente().getIdUtente())){
                           dettagliContrattoDTO.setNomeControparte(requestedContrattoUtente.getVenditore().getNome());
                           dettagliContrattoDTO.setCognomeControparte(requestedContrattoUtente.getVenditore().getCognome());
                       }else{
                           dettagliContrattoDTO.setNomeControparte(requestedContrattoUtente.getAcquirente().getNome());
                           dettagliContrattoDTO.setCognomeControparte(requestedContrattoUtente.getAcquirente().getCognome());
                       }
                       // ========================================================================================================================================

                       return Optional.of(dettagliContrattoDTO);
                   }else{
                       throw new ContrattoUtenteException("L'utente che ha richiesto i dettagli del contratto non è nè l'acquirente nè il venditore");
                   }
               }else{
                   throw new ContrattoUtenteException("Contratto non trovato");
               }
            }else{
                throw new ContrattoUtenteException("Impossibile richiedere il contratto desiderato");
            }
        }else{
            throw new UtenteException("Bisogna essere autenticati per avere la lista dei contratti");
        }
    }


    /**
     * Permette di prelevare un contratto dato id contratto e proprietario
     * @param authUser proprietario contratto
     * @param idContratto id contratto
     * @return il contratto voluto
     * @throws ContrattoUtenteException se non esiste l'associazione
     * @throws ContrattoException se non esiste il contratto
     */
    @Override
    public Optional<ContrattoUtente> getContrattoByIdUtenteAndIdContratto(Utente authUser, Long idContratto)
            throws ContrattoUtenteException, ContrattoException {
        if (authUser != null) {
            // prelevo il contratto dal database
            Optional<Contratto> tempContratto = contrattoService.getContrattoById(idContratto);
            if (tempContratto.isPresent()) {
                Contratto contratto = tempContratto.get();
                // prelevo la contrattazione tramite id contratto e id utente
                Optional<ContrattoUtente> tempFinalContratto =
                        contrattoUtenteRepo.getContrattoUtenteByIdContrattoAndIdUtente(contratto.getIdContratto(), authUser.getIdUtente());
                if (tempFinalContratto.isPresent()) {
                    return tempFinalContratto;
                } else {
                    throw new ContrattoUtenteException("Contratto non trovato");
                }
            }
        } else {
            throw new ContrattoUtenteException("Utente non autenticato per contratto");
        }
        return Optional.empty();
    }


}
