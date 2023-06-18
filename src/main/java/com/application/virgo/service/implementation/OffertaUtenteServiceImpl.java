package com.application.virgo.service.implementation;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.ListUnviewMessageDTO;
import com.application.virgo.exception.*;
import com.application.virgo.model.ComposedRelationship.CompoundKey.OffertaUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Contratto;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.OffertaUtenteJpaRepository;
import com.application.virgo.repositories.UtenteJpaRepository;
import com.application.virgo.service.interfaces.*;
import com.application.virgo.utilities.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class OffertaUtenteServiceImpl implements OffertaUtenteService{


    private final UtenteJpaRepository utenteRepo;
    private final OffertaUtenteJpaRepository offertaUtenteRepository;

    private final ImmobileService immobileService;
    private final OffertaService offertaService;
    private final ContrattoService contrattoService;
    private final ContrattoUtenteService contrattoUtenteService;

    private Optional<Utente> getInformationUtente(Long idUtenteToFound) throws UtenteException{
        Optional<Utente> tempUtente = utenteRepo.getUtenteByIdUtente(idUtenteToFound);
        if(tempUtente.isPresent()){
            return tempUtente;
        }else{
            throw new UtenteException("L'utente selezionato non esiste, se ne inserica un altro");
        }

    }

    private Optional<OfferteUtente> saveOffertaCommonMethod(Utente offerente, Offerta offertaProposta,
                                                            Utente utenteProprietario, Boolean madeByProprietario){

        // Creiamo prima la chiave primaria della relazione
        OffertaUtenteCompoundKey compoundKeyProprietario = new OffertaUtenteCompoundKey(utenteProprietario.getIdUtente(),
                offerente.getIdUtente(),
                offertaProposta.getIdOfferta());
        // Creiamo l'associazione tra l'offerente, il ricevente e l'offerta
        OfferteUtente offertaToProprietario = new OfferteUtente(compoundKeyProprietario,
                utenteProprietario, offerente,offertaProposta);

        if(madeByProprietario){
            offertaToProprietario.setVisionataDaPropietario(Boolean.TRUE);
        }else{
            offertaToProprietario.setVisionataDaPropietario(Boolean.FALSE);
        }


        return Optional.of(offertaUtenteRepository.save(offertaToProprietario));
    }
    @Override
    public Optional<OfferteUtente> saveOffertaToUtente(Utente offerente, Offerta offertaProposta, Long idVenditore)
            throws UtenteException, OffertaUtenteException {

        // Prelevo le informazioni dell'utente proprietario dell'immobile
        Optional<Utente> utenteProprietario = getInformationUtente(idVenditore);
        if(utenteProprietario.isPresent()){
            // se l'utente selezionato è il proprietario dell'immobile a cui stiamo facendo l'offerta...
            if(utenteProprietario.get().getIdUtente().equals(offertaProposta.getIdImmobileInteressato().getProprietario().getIdUtente())){
                // allora creiamo l'offerta
                saveOffertaCommonMethod(offerente, offertaProposta, utenteProprietario.get(), Boolean.TRUE);
            }else{
                throw new OffertaUtenteException("Non si può effetturare un'offerta su un immobile con proprietario diverso da quello indicato!");
            }


        }
        return Optional.empty();
    }

    @Override
    public Optional<OfferteUtente> rilanciaOffertaToUtente(Utente venditore, Offerta offertaProposta, Long idOfferente)
            throws UtenteException, OffertaUtenteException{

        Optional<Utente> utenteVenditore = getInformationUtente(idOfferente);

        if(utenteVenditore.isPresent()){
            // allora creiamo l'offerta
            saveOffertaCommonMethod(utenteVenditore.get(), offertaProposta, venditore, Boolean.FALSE);
        }else{
            throw new OffertaUtenteException("Non si può effetturare un'offerta su un immobile con proprietario diverso da quello indicato!");
        }

        return Optional.empty();
    }

    @Override
    public Page<OfferteUtente> getOfferteForUtenteProprietario(Utente authUser, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException {

        if(authUser != null){
            if(pageSize < Constants.PAGE_SIZE){
                return offertaUtenteRepository.getAllOfferteUtenteAsProprietario(
                        PageRequest.of(offset.intValue(), pageSize.intValue()),
                        authUser.getIdUtente()
                );
            }else{
                throw new OffertaUtenteException("La grandezza della pagina supera i " + Constants.PAGE_SIZE + " elementi");
            }
        }else{
            throw new UtenteException("L'utente deve essere autenticato");
        }

    }

    /**
     * Metodo comune per prelevare tutte le offerte scambiate tra due utenti
     * @param authUser utente autenticato che sta accettando o rifiutando
     * @param offerente colui che ha proposto l'offerta
     * @param idImmobile immobile interessato nella contrattazione
     * @param isProprietario permette di decidere se l'utente che sta facendo la richiesta, richiede tutte le offerte ricevute su immobili
     *                       di sua proprietà oppure vuole avere tutte le offerte proposte ad altri utenti
     * @return La lista delle offerte ricevute/inviate da un utente
     * @throws UtenteException Se l'utente non è autenticato
     * @throws ImmobileException se l'immobile non esiste
     */
    private List<OfferteUtente> logicProposteBetweenUtenti(Utente authUser, Utente offerente, Long idImmobile, Boolean isProprietario)
            throws UtenteException, ImmobileException {
        if (authUser != null){
            if(offerente != null){
                Optional<Immobile> immobile = immobileService.getImmobileInternalInformationById(idImmobile);
                if(immobile.isPresent()){
                    if(isProprietario){
                        return offertaUtenteRepository.getAllOfferteBetweenUtenti(authUser.getIdUtente(),
                                offerente.getIdUtente(), immobile.get().getIdImmobile());
                    }else{
                        return offertaUtenteRepository.getAllOfferteBetweenUtenti(offerente.getIdUtente(),
                                authUser.getIdUtente(), immobile.get().getIdImmobile());
                    }

                }else{
                    throw new ImmobileException("Immobile insesistente");
                }
            }else{
                throw new UtenteException("Impossibile reperire l'utente che ha proposto le offerte");
            }
        }else{
            throw new UtenteException("Impossibile reperire l'utente autenticato");
        }
    }

    @Override
    public List<OfferteUtente> allProposteBetweenUtenti(Utente authUser, Utente offerente, Long idImmobile)
            throws UtenteException, ImmobileException {
        return logicProposteBetweenUtenti(authUser, offerente, idImmobile, true);
    }

    @Override
    public List<OfferteUtente> allOfferteBetweenUtenti(Utente authUser, Utente offerente, Long idImmobile) throws UtenteException, ImmobileException {
        return logicProposteBetweenUtenti(authUser, offerente, idImmobile, false);
    }

    /**
     * Metodo comune che permette di accettare o declinare un'offerta
     * @param idOfferta offerta interessata
     * @param authUser utente autenticato
     * @param isAccettata se l'offerta è stata accettata o declinata
     * @return L'entità OffertaUtente aggiornata
     * @throws OffertaException Se non è stato possibile trovare l'offerta
     * @throws OffertaUtenteException se ci sono stati problemi con l'aggiornamento
     * @throws UtenteException se l'utente non è autenticato
     */
    private Optional<OfferteUtente> methodForAcceptAndDenyOfferta(Long idOfferta, Utente authUser, Boolean isAccettata)
            throws OffertaException, OffertaUtenteException, UtenteException{
        // controllo che l'utente è autenticato
        if(authUser != null){
            // prelevo l'offerta interessata
            Optional<Offerta> offertaSelezionata = offertaService.getOffertaDetails(idOfferta);
            // se presente
            if(offertaSelezionata.isPresent()){
                //Prelevo l'offerta in base al proprietario e al'id dell'offerta
                Optional<OfferteUtente> tempOffertaToAccept =
                        offertaUtenteRepository.getOfferteUtenteByProprietarioAndOffertaInteressata(authUser,offertaSelezionata.get());
                // se l'offerta voluta esiste
                if(tempOffertaToAccept.isPresent()){
                    // prelevo l'offerta interessata dall'optional
                    OfferteUtente offertaToAccept = tempOffertaToAccept.get();
                    //comunico se è stata accettata oppure no
                    offertaToAccept.setIsAccettato(isAccettata);
                    offertaToAccept.setIsDeclinato(!isAccettata);

                    //SE TRUE ALLORA HO ACCETTATO,
                    if(isAccettata){
                        offertaToAccept.setData_accettazione(Instant.now());
                    }else{
                        offertaToAccept.setData_declino(Instant.now());
                    }

                    return Optional.of(offertaUtenteRepository.save(offertaToAccept));
                }else{
                    throw new OffertaUtenteException("La richiesta di offerta non esiste!");
                }
            }else{
                throw new OffertaException("L'offerta non esiste!");
            }
        }else{
            throw new UtenteException("Bisogna essere autenticati per effettuare quest'azione!");
        }
    }


    @Override
    public Optional<ContrattoUtente> acceptOfferta(Long idOfferta, Utente proprietarioImmobile)
            throws ContrattoException, OffertaUtenteException, UtenteException, ImmobileException,
            OffertaException, ContrattoUtenteException {

        // Aggiorno l'offerta come accettata dall'utente
        Optional<OfferteUtente> tempAcceptedOfferta = methodForAcceptAndDenyOfferta(idOfferta, proprietarioImmobile, true);

        // Controllo che l'accettazione abbia avuto successo
        if(tempAcceptedOfferta.isPresent()){
            //Prelevo la classe OffertaUtente che rappresenta l'offerta tra due utenti
            OfferteUtente acceptedOfferta = tempAcceptedOfferta.get();

            acceptedOfferta.getOffertaInteressata().getIdImmobileInteressato().setIsEnabled(Boolean.FALSE);
            immobileService.internalImmobileUpdate(acceptedOfferta.getOffertaInteressata().getIdImmobileInteressato());


            //Dalla classe OffertaUtente prelevo i dati per la creazione del contratto
            Optional<Contratto> tempNewContratto = contrattoService.createNewContratto(
                    acceptedOfferta.getOffertaInteressata().getIdImmobileInteressato(),
                    acceptedOfferta.getOffertaInteressata().getPrezzoProposto()
            );

            //Controllo che la creazione del contratto sia andata a buon fine
            if(tempNewContratto.isPresent()){
                //Prelevo l'acquirente dell'immobile
                Optional<Utente> tempAcquirente = getInformationUtente(acceptedOfferta.getOfferente().getIdUtente());
                //Se l'ho prelevato con successo
                if(tempAcquirente.isPresent()){
                    //Creo l'associazione tra il contratto, il venditore e l'acquirente
                    Optional<ContrattoUtente> tempContrattoUtente = contrattoUtenteService.saveContrattoBetweenUtenti(
                            proprietarioImmobile, tempAcquirente.get(), tempNewContratto.get()
                    );

                    //Se la creazione dell'associazione è avvenuta correttamente
                    if(tempContrattoUtente.isPresent()){
                        //Ritorno il contratto creato correttamente
                        return tempContrattoUtente;
                    }else {
                        throw new ContrattoUtenteException("Impossibile creare il contratto fra gli utenti");
                    }
                }
            }else{
                throw new ContrattoException("Impossibile creare il contratto");
            }
        }else{
            throw new OffertaUtenteException("Impossibile accettare il contratto, riprovare!");
        }

        return Optional.empty();
    }

    @Override
    public Optional<OfferteUtente> declineOfferta(Long idOfferta, Utente authUser)
            throws OffertaException, OffertaUtenteException, UtenteException {
        return methodForAcceptAndDenyOfferta(idOfferta, authUser, false);
    }

    @Override
    public Page<OfferteUtente> getOfferteProposte(Utente authUser, Long offset, Long pageSize) throws OffertaUtenteException, UtenteException {
        if (authUser != null) {
            if (pageSize < Constants.PAGE_SIZE) {
                return offertaUtenteRepository.getAllOfferteUtenteAsOfferente(
                        PageRequest.of(offset.intValue(), pageSize.intValue()),
                        authUser.getIdUtente()
                );
            } else {
                throw new OffertaUtenteException("La grandezza della pagina supera i " + Constants.PAGE_SIZE + " elementi");
            }
        } else {
            throw new UtenteException("L'utente deve essere autenticato");
        }
    }

    @Override
    public List<ListUnviewMessageDTO> getListUnviewedMessaged() {
        return offertaUtenteRepository.getListUtenteWithUnreadMessages();
    }
}
