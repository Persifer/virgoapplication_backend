package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.DTO.outputDTO.GetUtenteImmobiliDTO;
import com.application.virgo.DTO.outputDTO.HomeImmobileDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import com.application.virgo.service.implementation.AuthServiceImpl;
import com.application.virgo.wrapperclass.SecuredUser;
import com.application.virgo.service.interfaces.ImmobileService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/site/immobile")
@Validated
@AllArgsConstructor
public class ImmobileController {

    private final ImmobileService immobileService;
    private final AuthServiceImpl authService;
    //private static final String URL_SUFFIX = "/immobile/";

    @GetMapping
    public String returnCreaImmobilePage(Model model){
        model.addAttribute("immobileDTO", new ImmobileDTO());
        return "CreaImmobile";
    }



    // Mapper per la creazione di un nuovo immobile associato ad singolo utente proprietario
                // /immobile/addnew
    @PostMapping(value = "/addnew", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String createNewImmobile(@ModelAttribute ImmobileDTO tempNewImmobile, ModelMap model
                                    /*@RequestPart("images") MultipartFile[] uploadedFile */) {

        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {

                Optional<ImmobileDTO> newImmobile = immobileService.createNewImmobile(tempNewImmobile,
                        authenticatedUser.get(),
                        tempNewImmobile.getUploadedFile());
                if (newImmobile.isPresent()) {
                    model.addAttribute("message", "Immobile creato con successo");
                    return "Home";
                } else {
                    model.addAttribute("error", "Errore nella creazione di un immobile, riprovare");
                    return "Fail";
                }
            }else{
                model.addAttribute("error", "Errore");
                return "Fail";
            }

        }catch (Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }


    // Mapper che permette di reperire i dati di un singolo immobile associato ad un utente tramite l'uso di GetImmobileInfoDTO
    // viene usato quando, dalla homepage, viene selezionato un immobile
    @GetMapping("/viewImmobile/{id_immobile}")
    public String getImmobileInformation(@PathVariable("id_immobile") Long idImmobile,
                                                                     ModelMap model){

        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                // se l'utente è autenticato allora posso vedere i dati del singolo immobile
                Optional<GetImmobileInfoDTO> storedImmobile = immobileService.getImmobileById(idImmobile);

                if(storedImmobile.isPresent()){
                    model.addAttribute("wantedImmobile", storedImmobile.get());
                    return "Immobile";
                }else{
                    model.addAttribute("error", "L'immobile voluto non è presente");
                    return "Fail";
                }
            }else{
                model.addAttribute("error", "Bisogna essere autenticato per richiedere un immobile");
                return "Login";
            }


        }catch (ImmobileException | UtenteException error){
            model.addAttribute("error", error.getMessage());
            System.out.println("oci");
            return "Fail";
        }
    }

    // Permette di ottenere i dati da modificare dell'immobile, serve per creare la pagina di modifica dei dati
    // PER PEPPE, DEVI FARE MODO CHE QUANDO L'UTENE CERCHI LA PAGINA DI MODIFICA PRIMA FA QUESTA RICHIESTA, METTE NEI CAMPI I VALORI GIA'
    // PRESENTI NEL DB NELLA PAGINA HTML E POI PUO' MODIFICARLO. SE HAI DUBBI CHIEDI
    @GetMapping("/infoToModify/{id_immobile}")
    public String getImmobileInformationForUpdate(@PathVariable("id_immobile") Long idImmobile, ModelMap model){
        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                // se l'utente è autenticato allora posso vedere i dati del singolo immobile
                Optional<ImmobileDTO> storedImmobile = immobileService.getImmobileByIdToUpdate(idImmobile, authenticatedUser.get());
                if(storedImmobile.isPresent()){
                    model.addAttribute("message", "Informazioni aggiornate con successo");
                    return "Immobile";
                }else{
                    model.addAttribute("error", "Errore nell'aggiornamento delle informazioni dell'utente");
                    return "Fail";
                }
            }else{
                model.addAttribute("error", "Bisogna esssere autenticati per aggiornare le informazioni");
                return "Login";
            }


        }catch (ImmobileException | UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }

    // Permette di far visualizzare all'utente la lista di tutti gli immobili da lui caricati
    @GetMapping("/getImmobiliUtente/{offset}/{pageSize}")
    public String getListaImmobiliUtente(@PathVariable("offset") Long offset,
                                         @PathVariable("pageSize") @Min(1) @Max(20) Long pageSize,
                                         ModelMap model){
        try{

            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                List<GetUtenteImmobiliDTO> foundedImmobili = immobileService.getUtenteListaImmobili(offset, pageSize,
                        authenticatedUser.get());
                if(!foundedImmobili.isEmpty()){
                    model.addAttribute("listaImmobili", foundedImmobili);
                    return "Utente";
                }else{
                    model.addAttribute("listaImmobili", List.of());
                    return "Utente";
                }
            }else{
                model.addAttribute("listaImmobili", "Bisogna essere autenticati per prendere questa informazione");
                return "Login";
            }

        }catch (ImmobileException | UtenteException error){
            model.addAttribute("listaImmobili", error.getMessage());
            return "Fail";
        }
    }

    // Permette di prendere le informazioni dal database senza riempire troppo la memoria heap di Java tramite paginazione
    // Offset -> indice da cui iniziare a prendere
    // PageSize -> quanti elementi prendere
    @GetMapping("/list/{offset}/{pageSize}")
    public String getListImmobili(@PathVariable("offset") Long offset,
                                  @PathVariable("pageSize") Long pageSize,
                                  ModelMap model){
        try{
            Optional<Utente> securedUser = authService.getAuthUtente();
            if(securedUser.isPresent()){
                List<HomeImmobileDTO> foundedImmobili = immobileService.getAllImmobiliPaginated(offset, pageSize);
                if(!foundedImmobili.isEmpty()){
                    model.addAttribute("listImmobili", foundedImmobili);
                    return "Home";
                }else{
                    model.addAttribute("error", "Dentro else 2");
                    return "Fail";
                }
            }else{
                model.addAttribute("error", "Dentro else 1");
                return "Fail";
            }

        }catch (Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }

    // È l'url che permette di aggiornare le informazioni di un immobile, quando viene richiamato si inviamo tutte le
    // informazioni modificate di un immobile
    @PutMapping("/updateInfo/{id_immobile}")
    public String modifyImmobileInfo(@ModelAttribute ImmobileDTO tempUpdatedimmobileDTO,
                                     @PathVariable("id_immobile") Long idImmobile,
                                     ModelMap model){
        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                Optional<Immobile> newImmobile = immobileService.updateImmobileInformation(tempUpdatedimmobileDTO,
                                                                        authenticatedUser.get(), idImmobile);
                if(newImmobile.isPresent()){
                    model.addAttribute("message", "Domanda inserita con successo");
                    return "Immobile";
                }else{
                    model.addAttribute("message", "Domanda inserita con successo");
                    return "Immobile";
                }
            }else{
                model.addAttribute("message", "Domanda inserita con successo");
                return "Immobile";
            }

        }catch (UtenteException | ImmobileException error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }

    }

}
