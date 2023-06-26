package com.application.virgo.service.implementation;

import com.application.virgo.DTO.Mapper.*;
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
import com.application.virgo.utilities.Constants;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ImmobileServiceImpl implements ImmobileService {

    private final ImmobileJpaRepository immobileRepo;

    private FileStorageService fileStorageService;

    private final ImmobileMapper mapperImmobile;
    private final ImmobileInformationMapper mapperInformation;
    private final HomeImmobileMapper mapperHomeInformation;
    private final ImmobiliDataUtente mapperUtenteInformation;
    private final DomandaImmobileMapper mapperDomande;

    /**
     * Metodo che permette l'upload delle foto di un immobile
     * @param uploadedFile lista delle foto da caricare
     * @param imageList stringa di partenza dell'immobile
     * @param savedImmobile l'immobile salvato
     * @param idProprietario il proprietario delle foto
     */
    private void uploadPhotosToImmobile(MultipartFile[] uploadedFile, String imageList,
                                        Immobile savedImmobile, Long idProprietario){

        StringBuilder uploadedImages;
        Integer iterator;
        String fileName = "";

        if(imageList==null || imageList.isBlank() || imageList.isEmpty()){
            uploadedImages = new StringBuilder();
            iterator = 1;
        }else{
            uploadedImages = new StringBuilder(imageList);
            iterator = Integer.parseInt(
                    uploadedImages.substring(uploadedImages.lastIndexOf("\\")+1, uploadedImages.length())
                                  .substring(0,1)
            );
        }

        if(uploadedFile.length != 0){
            for(MultipartFile file : uploadedFile){
                fileName = iterator+"_"+savedImmobile.getIdImmobile().toString()+"_"+file.getOriginalFilename();
                fileStorageService.save(file, idProprietario.toString(), fileName);
                uploadedImages
                        .append("http://localhost:8080/")
                        .append(fileStorageService.save(file, idProprietario.toString(), fileName))
                        .append(fileName)
                        .append("~");
                iterator++;
            }
        }else{
            uploadedImages = new StringBuilder("");
        }


        savedImmobile.setListaImmagini(uploadedImages.toString());

        immobileRepo.save(savedImmobile);
    }

    /**
     * Crea un nuovo immobile con le informazioni fornite, associandolo al proprietario specificato
     *
     * @param tempNewImmobile L'oggetto ImmobileDTO con le informazioni del nuovo immobile
     * @param utenteProprietario    Il proprietario dell'immobile
     * @return Un oggetto Optional contenente l'ImmobileDTO che rappresenta i dati dell'immobile salvato
     * @throws ImmobileException se non è possibile creare l'immobile
     * @throws UtenteException   se non esiste l'utente autenticato
     */
    @Override
    public Optional<ImmobileDTO> createNewImmobile(ImmobileDTO tempNewImmobile, Utente utenteProprietario)
        throws ImmobileException, UtenteException {
        LocalDate todayDate = LocalDate.now();
        if(utenteProprietario != null){
            //Controllo che le data di acquisizione sia minore di quella odierna
            if(tempNewImmobile.getDataAcquisizione().isBefore(todayDate)){
                //Controllo che le data di ultimo restuaro sia minore di quella odierna, può capitare che un restauro sia ancora in corso
                // ma meglio evitare
                if(tempNewImmobile.getDataUltimoRestauro().isBefore(todayDate)){

// ============================================== SALVATAGGIO IMMOBILE =================================================


                        // Inserisco la data di inserimento
                        tempNewImmobile.setDataCreazioneImmobile(todayDate);
                        // Conversione da ImmobileDTO a Immobile
                        Immobile newImmobile = mapperImmobile.apply(tempNewImmobile);
                        newImmobile.setProprietario(utenteProprietario);
                        // ====== SETTING LISTE
                        newImmobile.setDomandeImmobile(List.of());
                        // ===================
                        newImmobile.setIsEnabled(Boolean.TRUE);
                        newImmobile.setListaImmagini("");
                        // Salvataggio dell'immobile
                        Immobile savedImmobile = immobileRepo.save(newImmobile);

                        //Salvataggio delle immagini
                        uploadPhotosToImmobile(tempNewImmobile.getUploadedFile(), savedImmobile.getListaImmagini(),
                                savedImmobile,utenteProprietario.getIdUtente());

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

    /**
     * Ottiene le informazioni di un immobile dato l'id, è un metodo che viene usato "internamente" cioè da altri metodi
     * e non da metodi che vengono richiamati dall'utente
     *
     * @param idImmobile L'ID dell'immobile
     * @return Un oggetto Optional contenente l'Immobile se presente
     * @throws ImmobileException se non è possibile trovare l'immobile
     */
    @Override
    public Optional<Immobile> getImmobileInternalInformationById(Long idImmobile) throws ImmobileException{
        Optional<Immobile> requestImmobile = immobileRepo.getImmobilesByIdImmobile(idImmobile);
        if(requestImmobile.isPresent()){
            requestImmobile.get().setDomandeImmobile(immobileRepo.domandeImmobile(requestImmobile.get().getIdImmobile()));
            return requestImmobile;
        }else{
            throw new ImmobileException("L'immobile cercato non esiste, inserire un nuovo id");
        }
    }

    /**
     * Permette di prelevare le informazioni di un immobile quando lo richiede la stampa contratto
     * @param idImmobile  id immobile da prelevare
     * @return l'immobile selezionato
     * @throws ImmobileException se l'immobile non esiste
     */
    public Optional<Immobile> getImmobileInfoForContratto(Long idImmobile) throws ImmobileException{
        Optional<Immobile> requestImmobile = immobileRepo.getImmobilesByIdImmobileAfterContratto(idImmobile);
        if(requestImmobile.isPresent()){
            return requestImmobile;
        }else{
            throw new ImmobileException("L'immobile cercato non esiste, inserire un nuovo id");
        }
    }


    /**
     * Metodo comune a più metodi del sistema per reperire le informazioni di un immobile
     * @param tempImmobile immobile desiderato
     * @return l'immobile desiderato
     * @throws ImmobileException se l'immobile non esiste
     */
    private Optional<GetImmobileInfoDTO> setImmobileInformationCommonMethod(Optional<Immobile> tempImmobile) throws ImmobileException {
        if(tempImmobile.isPresent()){
            Immobile requestedImmobile = tempImmobile.get();
            requestedImmobile.setDomandeImmobile(immobileRepo.domandeImmobile(requestedImmobile.getIdImmobile()));
            GetImmobileInfoDTO immobileDTO = mapperInformation.apply(requestedImmobile);

            return Optional.of(immobileDTO);
        }else{
            throw new ImmobileException("L'immobile cercato non esiste!");
        }
    }

    /**
     * Ottiene le informazioni dell'immobile con l'ID specificato inserendole all'interno di un DTO che serve, unicamente,
     * per la rappresentazione nel front-end
     *
     * @param idImmobile L'ID dell'immobile
     * @return Un oggetto Optional contenente il GetImmobileInfoDTO se presente
     * @throws ImmobileException se non è possibile trovare l'immobile
     */
    @Override
    public Optional<GetImmobileInfoDTO> getImmobileById(Long idImmobile) throws ImmobileException{

        return setImmobileInformationCommonMethod(immobileRepo.getImmobilesByIdImmobile(idImmobile));

    }

    /**
     * Ottiene le informazioni dell'immobile con l'ID specificato dove l'utente che ha fatto la richiesta è
     * il proprietario dell'immobile
     *
     * @param idImmobile L'ID dell'immobile
     * @return Un oggetto Optional contenente il GetImmobileInfoDTO se presente
     * @throws ImmobileException se non è possibile trovare l'immobile
     */
    @Override
    public Optional<GetImmobileInfoDTO> getImmobileByIdAsProprietario(Utente authUser, Long idImmobile) throws ImmobileException{

        return setImmobileInformationCommonMethod(
                immobileRepo.getImmobilesByIdImmobileAsProprietario(idImmobile, authUser.getIdUtente()));

    }

    /**
     * Permette di eliminare un immobile
     * @param idImmobile immobile da eliminare
     * @param authUser utente proprietario immobile
     * @return numero righe affette
     */
    @Override
    public int immobileToDisable(Long idImmobile, Utente authUser){
        return immobileRepo.disableImmobile(idImmobile, authUser.getIdUtente());
    }

    /**
     * Aggiorna le informazioni interne dell'immobile specificato, essendo "interno" viene richiamato da un metodo
     * che, per lui, ha già fatto tutti i controlli
     *
     * @param immobileToUpdate L'immobile da aggiornare
     * @return True se l'aggiornamento è avvenuto correttamente, False altrimenti
     */
    public Boolean updateImmobileAfterAcceptance(Long immobileToUpdate) throws ImmobileException {
        // preleva le informazioni dell'immobili
        Optional<Immobile> immobile = getImmobileInternalInformationById(immobileToUpdate);
        if(immobile.isPresent()){
            Immobile getImmobile = immobile.get();
            getImmobile.setIsEnabled(Boolean.FALSE);
            immobileRepo.save(getImmobile);
        }
        return Boolean.TRUE;

    }

    /**
     * Ottiene una lista di immobili paginati per la pagina iniziale
     *
     * @param indiceIniziale L'indice di partenza per la paginazione
     * @param pageSize      La dimensione di pagina per la paginazione
     * @return Una lista di HomeImmobileDTO per la visualizzazione nella home page
     * @throws ImmobileException se si verifica un'eccezione relativa all'immobile
     */
    @Override
    public List<HomeImmobileDTO> getAllImmobiliPaginated(Long idUtente, Long indiceIniziale, Long pageSize) throws ImmobileException{

        if(indiceIniziale < pageSize - immobileRepo.countByIdImmobile() ){
            if(pageSize < Constants.PAGE_SIZE){
                // preleva gli immobili
                Page<Immobile> listImmobili =
                        immobileRepo.findAllByIsEnabledTrue(PageRequest.of(indiceIniziale.intValue(),
                                pageSize.intValue()), idUtente);
                // converte con la stream una page di immobili in una lista di getImmobileInfoDTO
                return listImmobili.stream().map(mapperHomeInformation).collect(Collectors.toList());
            }else {
                throw new ImmobileException("2 - Attenzione il numero dell'elemento da cui partire è troppo alto " + pageSize);
            }
        }else{
            throw new ImmobileException("1 -Attenzione il numero dell'elemento da cui partire è troppo alto " + (pageSize - immobileRepo.countByIdImmobile()));
        }
    }

    /**
     * Ottiene una lista di immobili paginati per l'utente loggato
     *
     * @param indiceIniziale L'indice di partenza per la paginazione
     * @param pageSize      La dimensione di pagina per la paginazione
     * @param authUser  L'utente proprietario degli immobili
     * @return Una lista di GetUtenteImmobiliDTO da inserire nella pagina di visualizzazione immobili di un utente
     * @throws ImmobileException se non è stato possibile trovare gli immobili
     * @throws UtenteException   se l'utente autenticato non esiste
     */
    @Override
    public List<GetUtenteImmobiliDTO> getUtenteListaImmobili(Long indiceIniziale, Long pageSize, Utente authUser)
            throws ImmobileException, UtenteException {
        if(authUser != null){
            if(indiceIniziale < pageSize -immobileRepo.countByIdImmobile() ){
                if(pageSize < Constants.PAGE_SIZE){
                    Page<Immobile> listImmobili = immobileRepo.getUtenteImmobiliList(authUser.getIdUtente(),
                            PageRequest.of(indiceIniziale.intValue(), pageSize.intValue()));
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

    /**
     * Permette di prelevare il titolo dell'immobile tramite id dell'immobile
     * @param idImmobile id immobile selezionato
     * @return immobile voluto
     * @throws ImmobileException se l'immobile non esiste
     */
    @Override
    public String getTitoloImmboileById(Long idImmobile) throws ImmobileException {
        Optional<Immobile> tempImmobile = immobileRepo.getImmobilesByIdImmobile(idImmobile);
        if(tempImmobile.isPresent()){
            return tempImmobile.get().getTitolo();
        }else{
            throw new ImmobileException("L'immobile cercato non esiste!");
        }
    }
}
