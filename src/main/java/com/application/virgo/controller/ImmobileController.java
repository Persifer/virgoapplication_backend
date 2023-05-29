package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
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

import java.util.List;
import java.util.Optional;

import static com.application.virgo.utilities.Constants.CONTROLLER_OUTPUT;

@Controller
@RequestMapping(path="/site")
@Validated
public class ImmobileController {

    private final ImmobileService immobileService;
    private static final String URL_SUFFIX = "/immobile/";

    public ImmobileController(ImmobileService immobileService) {
        this.immobileService = immobileService;
    }

    // Mapper per la creazione di un nuovo immobile associato ad singolo utente proprietario
                // /immobile/addnew
    @PostMapping(URL_SUFFIX + "/addnew")
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
    // il DTO
    @GetMapping(URL_SUFFIX+"/viewImmobile/{id_immobile}")
    public ResponseEntity<GetImmobileInfoDTO> getImmobileInformation(@PathVariable("id_immobile") Long idImmobile,
                                                                     @AuthenticationPrincipal SecuredUser authenticatedUser){
        try{
            if(authenticatedUser != null){
                // se l'utente è autenticato allora posso vedere i dati del singolo immobile
                Optional<GetImmobileInfoDTO> storedImmobile = immobileService.getImmobileById(idImmobile);
                if(storedImmobile.isPresent()){
                    return new ResponseEntity<GetImmobileInfoDTO>(storedImmobile.get(), HttpStatus.OK);
                }else{
                    return new ResponseEntity<GetImmobileInfoDTO>((GetImmobileInfoDTO) null, HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<GetImmobileInfoDTO>((GetImmobileInfoDTO) null, HttpStatus.UNAUTHORIZED);
            }


        }catch (ImmobileException error){
            return new ResponseEntity<GetImmobileInfoDTO>((GetImmobileInfoDTO) null, HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<GetImmobileInfoDTO>((GetImmobileInfoDTO) null, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    //Permette di ottenere i dati da modificare dell'immobile
    // PER PEPPE, DEVI FARE MODO CHE QUANDO L'UTENE CERCHI LA PAGINA DI MODIFICA PRIMA FA QUESTA RICHIESTA, METTE NEI CAMPI I VALORI GIA'
    // PRESENTI NEL DB NELLA PAGINA HTML E POI PUO' MODIFICARLO. SE HAI DUBBI CHIEDI
    @GetMapping(URL_SUFFIX+"/infoToModify/{id_immobile}")
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

    // Permette di prendere le informazioni dal database senza riempire troppo la memoria heap di Java tramite paginazione
    // Offset -> indice da cui iniziare a prendere
    // PageSize -> quanti elementi prendere
    @GetMapping("/list/{offset}/{pageSize}")
    public ResponseEntity<List<GetImmobileInfoDTO>> getListImmobili(@PathVariable Long offset,
                                                                    @PathVariable @Max(20) Long pageSize,
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

//    // TODO -> recupera dati utente dalla sessione
    // TODO -> recupero tutti gli immobili e recupero immobili tramite filtri
    @PutMapping("/updateInfo/{id_immobile}")
    public ResponseEntity<String> modifyImmobileInfo(@ModelAttribute ImmobileDTO tempUpdatedimmobileDTO,
                                                     @PathVariable Long idImmobile,
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


}
