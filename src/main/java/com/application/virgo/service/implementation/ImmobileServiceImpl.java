package com.application.virgo.service.implementation;

import com.application.virgo.DTO.Mapper.ImmobileInformationMapper;
import com.application.virgo.DTO.Mapper.ImmobileMapper;
import com.application.virgo.DTO.Mapper.ImmobiliDataUtente;
import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.DTO.outputDTO.GetUtenteImmobiliDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import com.application.virgo.repositories.ImmobileJpaRepository;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ImmobileServiceImpl implements ImmobileService {

    //TODO -> Implementa pagination all'interno di get all immobili


    private final ImmobileJpaRepository immobileRepo;
    private final ImmobileMapper mapperImmobile;
    private final UtenteService utenteService;
    private final ImmobileInformationMapper mapperInformation;
    private final ImmobiliDataUtente mapperUtenteInformation;


    @Override
    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile, Utente utenteProprietario)
        throws ImmobileException, UtenteException {
        // Prelevo l'utente dal db
        // TODO: Cambia prendendo i dati dell'utente dalla sessione di spring, COSI NON HA SENSO
        LocalDate todayDate = LocalDate.now();
        if(utenteProprietario != null){
            //Controllo che le data di acquisizione sia minore di quella odierna
            if(tempNewImmobile.getDataAcquisizione().isBefore(todayDate)){
                //Controllo che le data di ultimo restuaro sia minore di quella odierna, può capitare che un restauro sia ancora in corso
                // ma meglio evitare
                if(tempNewImmobile.getDataUltimoRestauro().isBefore(todayDate)){
                    // Inserisco la data di inserimento
                    tempNewImmobile.setDataCreazioneImmobile(todayDate);
                    // Conversione da ImmobileDTO a Immobile
                    Immobile newImmobile = mapperImmobile.apply(tempNewImmobile);
                    newImmobile.setProprietario(utenteProprietario);
                    // ====== SETTING LISTE
                    newImmobile.setDomandeImmobile(Set.of());
                    // ===================

                    // Salvataggio dell'immobile
                    immobileRepo.save(newImmobile);
                    return Optional.of(tempNewImmobile);
                }else{
                    throw new ImmobileException("La data di ultimo restauro immobile è superiore a quella odierna!");
                }
            }else{
                throw new ImmobileException("La data di acqusizione immobile è superiore a quella odierna!");
            }
        }else{
            throw new UtenteException("Utente proprietario non trovato");
        }

    }

    @Override
    public Optional<GetImmobileInfoDTO> getImmobileById(Long idImmobile) throws ImmobileException{

        Optional<Immobile> tempImmobile = immobileRepo.getImmobilesByIdImmobile(idImmobile);
        if(tempImmobile.isPresent()){
            Immobile requestedImmobile = tempImmobile.get();
            return Optional.of(mapperInformation.apply(requestedImmobile));

        }else{
            throw new ImmobileException("L'immobile cercato non esiste!");
        }


    }

    //Restituisce i dati da modificare di un singolo immobile
    @Override
    public Optional<ImmobileDTO> getImmobileByIdToUpdate(Long idImmobile, Utente authUser)
            throws ImmobileException, UtenteException {
        Optional<Immobile> tempToUpdateImmobile = immobileRepo.getImmobilesByIdImmobile(idImmobile);
        if(tempToUpdateImmobile.isPresent()){

            Immobile toUpdateImmobile = tempToUpdateImmobile.get();
            // controllo che il proprietario dell'immobile selezionato sia lo stesso di quello loggato che, si presume, sia l'utente
            // proprietario dell'immobile
            if(toUpdateImmobile.getProprietario().getIdUtente().equals(authUser.getIdUtente())){
                ImmobileDTO immobileToUpdate = mapperImmobile.apply(toUpdateImmobile);
                return Optional.of(immobileToUpdate);
            }else{
                throw new UtenteException("Non sei autorizzato a modificare i dati di questo immobile!");
            }
        }else{
            throw new ImmobileException("L'immobile selezionato non esiste");
        }
    }

    // classe che permette l'attuazione delle modifiche dei dati di un singolo immobile dato:
    //   -> DTO con i nuovi dati
    //   -> proprietario dell'immobile
    //   -> immobile da modificare
    @Override
    public Optional<Immobile> updateImmobileInformation(ImmobileDTO tempUpdatedImmobile, Utente authUser, Long idImmobileToUpdate)
            throws ImmobileException, UtenteException {
        // prelevo i dati dell'immobile di cui vorrei aggiornare i dati per il confronto
        Optional<Immobile> tempToCheckImmobile = immobileRepo.getImmobilesByIdImmobile(idImmobileToUpdate);
        String error = "";
        // sel'immobile esiste allora continuo
        if(tempToCheckImmobile.isPresent()){
            //prendo la classe immobile dentro l'optional
            Immobile toCheckImmobile = tempToCheckImmobile.get();
            // controllo che il proprietario dell'immobile selezionato sia lo stesso di quello loggato che, si presume, sia l'utente
            // proprietario dell'immobile
            if(toCheckImmobile.getProprietario().getIdUtente().equals(authUser.getIdUtente())){

                // controllo quali sono i campi che sono stati cambiati
                if(!toCheckImmobile.getDataAcquisizione().equals(tempUpdatedImmobile.getDataAcquisizione())){
                    if(tempUpdatedImmobile.getDataAcquisizione() != null ||
                            tempUpdatedImmobile.getDataAcquisizione().isAfter(LocalDate.now())){
                        toCheckImmobile.setDataAcquisizione(tempUpdatedImmobile.getDataAcquisizione());
                    }else{
                        error += "La data di acquisizione non esiste oppure è troppo grande!\n";
                    }
                }

                if(!toCheckImmobile.getDataUltimoRestauro().equals(tempUpdatedImmobile.getDataUltimoRestauro())){
                    // è possibile che l'ultimo restuaro sia programmato e non ancora avvenuto oppure non sia presente
                    toCheckImmobile.setDataUltimoRestauro(tempUpdatedImmobile.getDataUltimoRestauro());
                }

                if(!toCheckImmobile.getDescrizione().equals(tempUpdatedImmobile.getDescrizione())){
                    // do la possibilità all'utente di togliere la descrizione
                    if(tempUpdatedImmobile.getDescrizione().isEmpty() || tempUpdatedImmobile.getDescrizione().isBlank()
                        || tempUpdatedImmobile.getDescrizione() == null)
                    {
                        toCheckImmobile.setDescrizione("");
                    }else{
                        toCheckImmobile.setDescrizione(tempUpdatedImmobile.getDescrizione());
                    }
                }
// ============================================= RESIDENZA =============================================================
                if(!toCheckImmobile.getPrezzo().equals(tempUpdatedImmobile.getPrezzo())){
                    if(tempUpdatedImmobile.getPrezzo() != null){
                        toCheckImmobile.setPrezzo(tempUpdatedImmobile.getPrezzo());
                    }else{
                        error += "Il prezzo non può essere vuoto!\n";
                    }
                }

                if(!toCheckImmobile.getCap().equals(tempUpdatedImmobile.getCap())){
                    if(tempUpdatedImmobile.getPrezzo() != null){
                        toCheckImmobile.setCap(tempUpdatedImmobile.getCap());
                    }else{
                        error += "Il CAP non può essere vuoto!\n";
                    }
                }

                if(!toCheckImmobile.getCitta().equals(tempUpdatedImmobile.getCitta())){
                    if(tempUpdatedImmobile.getPrezzo() != null){
                        toCheckImmobile.setCitta(tempUpdatedImmobile.getCitta());
                    }else{
                        error += "La città non può essere vuota!\n";
                    }
                }

                if(!toCheckImmobile.getProvincia().equals(tempUpdatedImmobile.getProvincia())){
                    if(tempUpdatedImmobile.getPrezzo() != null){
                        toCheckImmobile.setProvincia(tempUpdatedImmobile.getProvincia());
                    }else{
                        error += "La provincia non può essere vuota!\n";
                    }
                }

                if(!toCheckImmobile.getVia().equals(tempUpdatedImmobile.getVia())){
                    if(tempUpdatedImmobile.getPrezzo() != null){
                        toCheckImmobile.setVia(tempUpdatedImmobile.getVia());
                    }else{
                        error += "La via non può essere vuota!\n";
                    }
                }

// =====================================================================================================================

                if(error.isBlank() || error.isEmpty()){

                    immobileRepo.save(toCheckImmobile);
                    return Optional.of(toCheckImmobile);
                }else{
                    throw new ImmobileException(error);
                }

            }else{
                throw new UtenteException("Non sei autorizzato a modificare i dati di questo immobile!");
            }
        }else{
            throw new ImmobileException("L'immobile selezionato non esiste");
        }

    }

    @Override
    public List<GetImmobileInfoDTO> getAllImmobiliPaginated(Long inidiceIniziale, Long pageSize) throws ImmobileException{

        if(inidiceIniziale > immobileRepo.countByIdImmobile() - pageSize){
            if(pageSize > 20){
                Page<Immobile> listImmobili = immobileRepo.findAll(PageRequest.of(inidiceIniziale.intValue(), pageSize.intValue()));
                // converte con la stream una page di immobili in una lista di getImmobileInfoDTO
                return listImmobili.stream().map(mapperInformation).collect(Collectors.toList());
            }else {
                throw new ImmobileException("Attenzione il numero dell'elemento da cui partire è troppo alto");
            }
        }else{
            throw new ImmobileException("Attenzione il numero dell'elemento da cui partire è troppo alto");
        }
    }

    @Override
    public List<GetImmobileInfoDTO> getFilteredImmobiliPaginated(String filter) {
        return null;
    }

    @Override
    public List<GetImmobileInfoDTO> getImmobiliByKeyword(String keyword) {
        return null;
    }

    @Override
    public List<GetUtenteImmobiliDTO> getUtenteListaImmobili(Long inidiceIniziale, Long pageSize, Utente authUser)
            throws ImmobileException, UtenteException {
        if(authUser != null){
            if(inidiceIniziale > immobileRepo.countByIdImmobile() - pageSize){
                if(pageSize > 20){
                    Page<Immobile> listImmobili = immobileRepo.getUtenteImmobiliList(authUser.getIdUtente(),
                            PageRequest.of(inidiceIniziale.intValue(), pageSize.intValue()));
                    // converte con la stream una page di immobili in una lista di getImmobileInfoDTO
                    return listImmobili.stream().map(mapperUtenteInformation).collect(Collectors.toList());

                }else {
                    throw new ImmobileException("Attenzione il numero dell'elemento da cui partire è troppo alto");
                }
            }else{
                throw new ImmobileException("Attenzione il numero dell'elemento da cui partire è troppo alto");
            }
        }else{
            throw new UtenteException("Bisogna essere loggati per poter ottenere le informazioni");
        }

    }

    @Override
    public Optional<Immobile> addNewDomandaToImmobile(DomandaDTO tempDomanda, Utente authUser, Long idImmobileInteressato)
            throws ImmobileException, UtenteException {

        // controllo che ci sia effettivamente un utente loggato
        if(authUser != null){
            // prelevo l'immobile dal database
            Optional<Immobile> tempImmobileInteressato = immobileRepo.getImmobilesByIdImmobile(idImmobileInteressato);

            if(tempImmobileInteressato.isPresent()){
                Immobile immobileInteressato = tempImmobileInteressato.get();

                // creo la nuova domanda e l'aggiungo all'immobile
                Domanda newDomanda = new Domanda(tempDomanda.getContenuto(), LocalDate.now());
                immobileInteressato.getDomandeImmobile().add(newDomanda);

                // aggiungo la domanda all'utente
                utenteService.addDomandaToUtente(authUser, newDomanda);

                return Optional.of(immobileRepo.save(immobileInteressato));
            }else{
                throw new ImmobileException("Attenzione l'immobile selezionato non esiste");
            }
        }else{
            throw new UtenteException("Bisogna essere loggati per poter pubblicare una domanda");
        }

    }
}
