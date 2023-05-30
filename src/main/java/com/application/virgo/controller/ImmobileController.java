package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.DomandaImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.DTO.outputDTO.GetUtenteImmobiliDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import com.application.virgo.wrapperclass.SecuredUser;
import com.application.virgo.service.interfaces.ImmobileService;
import jakarta.validation.constraints.Max;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import static com.application.virgo.utilities.Constants.CONTROLLER_OUTPUT;

@Controller
@RequestMapping(path="/site/immobile")
@Validated
public class ImmobileController {

    private final ImmobileService immobileService;
    //private static final String URL_SUFFIX = "/immobile/";

    public ImmobileController(ImmobileService immobileService) {
        this.immobileService = immobileService;
    }

    // Mapper per la creazione di un nuovo immobile associato ad singolo utente proprietario
                // /immobile/addnew
    @PostMapping("/addnew")
    public ResponseEntity<String> createNewImmobile(@ModelAttribute ImmobileDTO tempNewImmobile,
                                                    @AuthenticationPrincipal SecuredUser authenticatedUser){
        try{
            if(authenticatedUser != null){
                Optional<ImmobileDTO> newImmobile = immobileService.createNewImmobile(tempNewImmobile, authenticatedUser.getUtenteInformation());
                if(newImmobile.isPresent()){
                    return new ResponseEntity<String>("Immobile registrato correttamente", HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("Errore nella registrazione di un nuovo immobile", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<String>("Effettuare il login per creare un nuovo immboile", HttpStatus.UNAUTHORIZED);
            }

        }catch (ImmobileException | UtenteException error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    // Mapper che permette di reperire i dati di un singolo immobile associato ad un utente tramite l'uso di GetImmobileInfoDTO
    // viene usato quando, dalla homepage, viene selezionato un immobile
    @GetMapping("/viewImmobile/{id_immobile}")
    public ResponseEntity<GetImmobileInfoDTO> getImmobileInformation(@PathVariable("id_immobile") Long idImmobile,
                                                                     @AuthenticationPrincipal SecuredUser authenticatedUser){
        try{
            if(authenticatedUser != null){
                // se l'utente è autenticato allora posso vedere i dati del singolo immobile
                Optional<GetImmobileInfoDTO> storedImmobile = immobileService.getImmobileById(idImmobile);
                if(storedImmobile.isPresent()){
                    return new ResponseEntity<>(storedImmobile.get(), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>((GetImmobileInfoDTO) null, HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>((GetImmobileInfoDTO) null, HttpStatus.UNAUTHORIZED);
            }


        }catch (ImmobileException error){
            return new ResponseEntity<>((GetImmobileInfoDTO) null, HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<>((GetImmobileInfoDTO) null, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    // Permette di ottenere i dati da modificare dell'immobile, serve per creare la pagina di modifica dei dati
    // PER PEPPE, DEVI FARE MODO CHE QUANDO L'UTENE CERCHI LA PAGINA DI MODIFICA PRIMA FA QUESTA RICHIESTA, METTE NEI CAMPI I VALORI GIA'
    // PRESENTI NEL DB NELLA PAGINA HTML E POI PUO' MODIFICARLO. SE HAI DUBBI CHIEDI
    @GetMapping("/infoToModify/{id_immobile}")
    public ResponseEntity<ImmobileDTO> getImmobileInformationForUpdate(@PathVariable("id_immobile") Long idImmobile,
                                                                     @AuthenticationPrincipal SecuredUser authenticatedUser){
        try{
            if(authenticatedUser != null){
                // se l'utente è autenticato allora posso vedere i dati del singolo immobile
                Optional<ImmobileDTO> storedImmobile = immobileService.getImmobileByIdToUpdate(idImmobile, authenticatedUser.getUtenteInformation());
                if(storedImmobile.isPresent()){
                    return new ResponseEntity<ImmobileDTO>(storedImmobile.get(), HttpStatus.OK);
                }else{
                    return new ResponseEntity<ImmobileDTO>((ImmobileDTO) null, HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<ImmobileDTO>((ImmobileDTO) null, HttpStatus.UNAUTHORIZED);
            }


        }catch (ImmobileException error){
            return new ResponseEntity<ImmobileDTO>((ImmobileDTO) null, HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<ImmobileDTO>((ImmobileDTO) null, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    // Permette di far visualizzare all'utente la lista di tutti gli immobili da lui caricati
    @GetMapping("/getImmobiliUtente/{offset}/{pageSize}")
    public ResponseEntity<List<GetUtenteImmobiliDTO>> getListaImmobiliUtente(@PathVariable("offset") Long offset,
                                                                             @PathVariable("pageSize") @Max(20) Long pageSize,
                                                                             @AuthenticationPrincipal SecuredUser authUser){
        try{
            if(authUser != null){
                List<GetUtenteImmobiliDTO> foundedImmobili = immobileService.getUtenteListaImmobili(offset, pageSize, authUser.getUtenteInformation());
                if(!foundedImmobili.isEmpty()){
                    return new ResponseEntity<>(foundedImmobili, HttpStatus.BAD_REQUEST);
                }else{
                    return new ResponseEntity<>(List.of(), HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<>(List.of(), HttpStatus.UNAUTHORIZED);
            }

        }catch (ImmobileException error){
            return new ResponseEntity<>(List.of(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Permette di prendere le informazioni dal database senza riempire troppo la memoria heap di Java tramite paginazione
    // Offset -> indice da cui iniziare a prendere
    // PageSize -> quanti elementi prendere
    @GetMapping("/list/{offset}/{pageSize}")
    public ResponseEntity<List<GetImmobileInfoDTO>> getListImmobili(@PathVariable("offset") Long offset,
                                                                    @PathVariable("pageSize") @Max(20) Long pageSize,
                                                                    @AuthenticationPrincipal SecuredUser securedUser ){
        try{
            if(securedUser != null){
                List<GetImmobileInfoDTO> foundedImmobili = immobileService.getAllImmobiliPaginated(offset, pageSize);
                if(!foundedImmobili.isEmpty()){
                    return new ResponseEntity<List<GetImmobileInfoDTO>>(foundedImmobili, HttpStatus.BAD_REQUEST);
                }else{
                    return new ResponseEntity<List<GetImmobileInfoDTO>>(List.of(), HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<List<GetImmobileInfoDTO>>(List.of(), HttpStatus.UNAUTHORIZED);
            }

        }catch (ImmobileException error){
            return new ResponseEntity<List<GetImmobileInfoDTO>>(List.of(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<List<GetImmobileInfoDTO>>(List.of(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // È l'url che permette di aggiornare le informazioni di un immobile, quando viene richiamato si inviamo tutte le
    // informazioni modificate di un immobile
    @PutMapping("/updateInfo/{id_immobile}")
    public ResponseEntity<String> modifyImmobileInfo(@ModelAttribute ImmobileDTO tempUpdatedimmobileDTO,
                                                     @PathVariable("id_immobile") Long idImmobile,
                                                     @AuthenticationPrincipal SecuredUser authenticatedUser){
        try{
            if(authenticatedUser != null){
                Optional<Immobile> newImmobile = immobileService.updateImmobileInformation(tempUpdatedimmobileDTO,
                                                                        authenticatedUser.getUtenteInformation(), idImmobile);
                if(newImmobile.isPresent()){
                    return new ResponseEntity<String>("Immobile registrato correttamente", HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("Errore nella registrazione di un nuovo immobile", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<String>("Effettuare il login per creare un nuovo immboile", HttpStatus.UNAUTHORIZED);
            }

        }catch (UtenteException error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.UNAUTHORIZED);
        }        catch (ImmobileException error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @PostMapping("/addQuestion/{id_immobile}")
    public ResponseEntity<String> addDomandaToImmobile(@ModelAttribute DomandaDTO tempNewDomandaDTO,
                                                        @PathVariable("id_immobile") Long idImmobile,
                                                         @AuthenticationPrincipal SecuredUser authenticatedUser){

        try{
            if(authenticatedUser != null){
                Optional<Immobile> newImmobile = immobileService.addNewDomandaToImmobile(tempNewDomandaDTO,
                        authenticatedUser.getUtenteInformation(), idImmobile);
                if(newImmobile.isPresent()){
                    return new ResponseEntity<String>("Domanda pubblicata correttamente", HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("Errore nella pubblicazione di una nuova domanda", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<String>("Effettuare il login per creare inserire una domanda", HttpStatus.UNAUTHORIZED);
            }

        }catch (UtenteException error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.UNAUTHORIZED);
        }        catch (ImmobileException error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }


}
