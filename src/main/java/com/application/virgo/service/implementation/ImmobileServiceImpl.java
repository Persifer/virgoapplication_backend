package com.application.virgo.service.implementation;

import com.application.virgo.DTO.Mapper.*;
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
import com.application.virgo.repositories.ImmobileJpaRepository;
import com.application.virgo.service.interfaces.FileStorageService;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.UtenteService;
import com.application.virgo.utilities.Constants;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.Arrays;
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

    private FileStorageService fileStorageService;

    private final ImmobileMapper mapperImmobile;
    private final ImmobileInformationMapper mapperInformation;
    private final HomeImmobileMapper mapperHomeInformation;
    private final ImmobiliDataUtente mapperUtenteInformation;
    private final DomandaImmobileMapper mapperDomande;


    @Override
    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile, Utente utenteProprietario, MultipartFile[] uploadedFile)
        throws ImmobileException, UtenteException {
        LocalDate todayDate = LocalDate.now();
        if(utenteProprietario != null){
            //Controllo che le data di acquisizione sia minore di quella odierna
            if(tempNewImmobile.getDataAcquisizione().isBefore(todayDate)){
                //Controllo che le data di ultimo restuaro sia minore di quella odierna, può capitare che un restauro sia ancora in corso
                // ma meglio evitare
                if(tempNewImmobile.getDataUltimoRestauro().isBefore(todayDate)){
                    if(tempNewImmobile.getMetriQuadri().equalsIgnoreCase("0") ||
                            tempNewImmobile.getMetriQuadri().isBlank() || tempNewImmobile.getMetriQuadri().isEmpty()){
// ============================================== SALVATAGGIO IMMOBILE =================================================
                        StringBuilder uploadedImages = new StringBuilder();
                        int iterator = 1;
                        String fileName = "";

                        // Inserisco la data di inserimento
                        tempNewImmobile.setDataCreazioneImmobile(todayDate);
                        // Conversione da ImmobileDTO a Immobile
                        Immobile newImmobile = mapperImmobile.apply(tempNewImmobile);
                        newImmobile.setProprietario(utenteProprietario);
                        // ====== SETTING LISTE
                        newImmobile.setDomandeImmobile(Set.of());
                        // ===================

                        // Salvataggio dell'immobile
                        Immobile savedImmobile = immobileRepo.save(newImmobile);

                        //Salvataggio delle immagini
                        for(MultipartFile file : uploadedFile){
                            fileName = iterator+"_"+savedImmobile.getIdImmobile().toString()+"_"+file.getName();

                            uploadedImages
                                    .append(fileStorageService.save(file, utenteProprietario.getIdUtente().toString(), fileName))
                                    .append(fileName)
                                    .append("|");
                            iterator++;
                        }

                        savedImmobile.setListaImmagini(uploadedFile.toString());

                        immobileRepo.save(savedImmobile);

                        return Optional.of(tempNewImmobile);
                    }else{
                        throw new ImmobileException("Il numero di metri quadri non può essere vuoto o 0");
                    }

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

    // Metodo che permette di ottenere le informazioni di un immobile dal database. È un metodo "interno" che non
    // serve per esporre dati al front-end e quindi, l'unico controllo che fa, è per vedere se l'immobile esiste
    @Override
    public Optional<Immobile> getImmobileInternalInformationById(Long idImmobile) throws ImmobileException{
        Optional<Immobile> requestImmobile = immobileRepo.getImmobilesByIdImmobile(idImmobile);

        if(requestImmobile.isPresent()){
            return requestImmobile;
        }else{
            throw new ImmobileException("L'immobile cercato non esiste, inserire un nuovo id");
        }
    }

    @Override
    public Optional<GetImmobileInfoDTO> getImmobileById(Long idImmobile) throws ImmobileException{

        Optional<Immobile> tempImmobile = immobileRepo.getImmobilesByIdImmobile(idImmobile);
        if(tempImmobile.isPresent()){
            Immobile requestedImmobile = tempImmobile.get();
            GetImmobileInfoDTO immobileDTO = mapperInformation.apply(requestedImmobile);

            immobileDTO.setListaDomandeImmobile(requestedImmobile.getDomandeImmobile()
                                                                    .stream()
                                                                    .map(mapperDomande)
                                                                    .collect(Collectors.toList()));
            return Optional.of(immobileDTO);
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

                if(!toCheckImmobile.getLocali().equals(tempUpdatedImmobile.getLocali())){
                    // controllo sul numero dei locali
                    if(tempUpdatedImmobile.getLocali() == null ||
                            tempUpdatedImmobile.getLocali().isEmpty() || tempUpdatedImmobile.getLocali().isBlank()){
                        error += "Il numero dei locali non può essere vuoto o 0\n";
                    }else{
                        toCheckImmobile.setLocali(tempUpdatedImmobile.getLocali());
                    }

                }

                if(!toCheckImmobile.getMetriQuadri().equals(tempUpdatedImmobile.getMetriQuadri())){
                    // controllo sul numero dei locali
                    if(tempUpdatedImmobile.getMetriQuadri() == null ||
                            tempUpdatedImmobile.getMetriQuadri().isEmpty() || tempUpdatedImmobile.getMetriQuadri().isBlank()){
                        error += "Il numero di metri quadri non può essere vuoto o 0\n";
                    }else{
                        toCheckImmobile.setMetriQuadri(tempUpdatedImmobile.getMetriQuadri());
                    }

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

                toCheckImmobile.setIsEnabled(tempUpdatedImmobile.getIsEnabled());

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

    public Boolean internalImmobileUpdate(Immobile immobileToUpdate){
        immobileRepo.save(immobileToUpdate);
        return Boolean.TRUE;

    }

    @Override
    public List<HomeImmobileDTO> getAllImmobiliPaginated(Long inidiceIniziale, Long pageSize) throws ImmobileException{

        if(inidiceIniziale < pageSize - immobileRepo.countByIdImmobile() ){
            if(pageSize < Constants.PAGE_SIZE){
                Page<Immobile> listImmobili = immobileRepo.findAll(PageRequest.of(inidiceIniziale.intValue(), pageSize.intValue()));
                // converte con la stream una page di immobili in una lista di getImmobileInfoDTO
                return listImmobili.stream().map(mapperHomeInformation).collect(Collectors.toList());
            }else {
                throw new ImmobileException("2 - Attenzione il numero dell'elemento da cui partire è troppo alto " + pageSize);
            }
        }else{
            throw new ImmobileException("1 -Attenzione il numero dell'elemento da cui partire è troppo alto " + (pageSize - immobileRepo.countByIdImmobile()));
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
            if(inidiceIniziale < pageSize -immobileRepo.countByIdImmobile() ){
                if(pageSize < Constants.PAGE_SIZE){
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
    public Optional<Immobile> addNewDomandaToImmobile(Domanda domanda, Utente authUser, Long idImmobileInteressato)
            throws ImmobileException, UtenteException {

        // controllo che ci sia effettivamente un utente loggato
        if(authUser != null){
            // prelevo l'immobile dal database
            Optional<Immobile> tempImmobileInteressato = immobileRepo.getImmobilesByIdImmobile(idImmobileInteressato);

            if(tempImmobileInteressato.isPresent()){
                Immobile immobileInteressato = tempImmobileInteressato.get();
                // aggiungo la domanda all'immobile
                immobileInteressato.getDomandeImmobile().add(domanda);

                // aggiungo la domanda all'utente
                //utenteService.addDomandaToUtente(authUser, newDomanda);

                return Optional.of(immobileRepo.save(immobileInteressato));
            }else{
                throw new ImmobileException("Attenzione l'immobile selezionato non esiste");
            }
        }else{
            throw new UtenteException("Bisogna essere loggati per poter pubblicare una domanda");
        }

    }
}
