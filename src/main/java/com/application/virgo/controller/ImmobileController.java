package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.DTO.outputDTO.DomandaImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.DTO.outputDTO.GetUtenteImmobiliDTO;
import com.application.virgo.DTO.outputDTO.HomeImmobileDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import com.application.virgo.service.implementation.AuthServiceImpl;
import com.application.virgo.service.interfaces.AuthService;
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

/**
 * Classe che contiene tutti gli endpoint per la gestione degli immobili
 */
@Controller
@RequestMapping(path="/site/immobile")
@Validated
@AllArgsConstructor
public class ImmobileController {

    private final ImmobileService immobileService;
    private final AuthService authService;
    //private static final String URL_SUFFIX = "/immobile/";

    /**
     * Ritorna la view per la creazione di un immobile
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @return Ritorna la view per la creazione di un immobile
     */
    @GetMapping
    public String returnCreaImmobilePage(ModelMap model){
        model.addAttribute("immobileDTO", new ImmobileDTO());
        return "CreaImmobile";
    }



    /** Mapper per la creazione di un nuovo immobile associato ad singolo utente proprietario
     * /immobile/addnew
     **/
    @PostMapping(value = "/addnew", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String createNewImmobile(@ModelAttribute ImmobileDTO tempNewImmobile, ModelMap model) {

        try{
            // controllo l'utente autenticato
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                // prelevo le informaizoni dell'immobile appena creato dalla business logic
                Optional<ImmobileDTO> newImmobile = immobileService.createNewImmobile(tempNewImmobile,
                        authenticatedUser.get());
                if (newImmobile.isPresent()) {
                    // se tutto okay ritorno alla home
                    model.addAttribute("message", "Immobile creato con successo");
                    return "Home";
                } else {
                    model.addAttribute("error", "Errore nella creazione di un immobile, riprovare");
                    return "Fail1";
                }
            }else{
                model.addAttribute("error", "Errore");
                return "Fail2";
            }

        }catch (Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail3";
        }
    }


    /**
     * Mapper che permette di reperire i dati di un singolo immobile associato ad un utente tramite l'uso di GetImmobileInfoDTO
     * viene usato quando, dalla homepage, viene selezionato un immobile
     * @param idImmobile id dell'immobile da selezionare
     * @param model classe per il passaggio dei dati al front-end
     * @return
     */
    @GetMapping("/viewImmobile/{id_immobile}")
    public String getImmobileInformation(@PathVariable("id_immobile") Long idImmobile,
                                                                     ModelMap model){

        try{
            // controllo che l'utente è autenticato
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                // se l'utente è autenticato allora posso vedere i dati del singolo immobile
                Optional<GetImmobileInfoDTO> storedImmobile = immobileService.getImmobileById(idImmobile);

                if(storedImmobile.isPresent()){
                    // se tutto okay allora vado alla pagina immobile
                    model.addAttribute("wantedImmobile", storedImmobile.get());
                    model.addAttribute("tempNewDomandaDTO", new DomandaDTO());
                    model.addAttribute("tempOffertaDTO", new InsertOffertaDTO());

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

    /**
     * Permette di arrivare alla view per gestire un immobile da parte dell'utentre proprietario
     * @param idImmobile id immobile da visionare
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @return la view dove l'utente modificherà i dati dell'immobile scelto
     */
    @GetMapping("/mioImmobile/{id_immobile}")
    public String getImmobileInformationAsProprietarioImmobile(@PathVariable("id_immobile") Long idImmobile,
                                         ModelMap model){
        try{
            // controlla che l'utente è autenticato
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                // se l'utente è autenticato allora posso vedere i dati del singolo immobile
                Optional<GetImmobileInfoDTO> storedImmobile =
                        immobileService.getImmobileByIdAsProprietario(authenticatedUser.get(), idImmobile);
                if(storedImmobile.isPresent()){
                    // se è tutto okay vado alla pagina immobile
                    model.addAttribute("wantedImmobile", storedImmobile.get());
                    model.addAttribute("tempNewDomandaDTO", new DomandaDTO());
                    model.addAttribute("tempOffertaDTO", new InsertOffertaDTO());

                    return "MioImmobile";
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

    /**
     * Permette di eliminare un immobile di un utente
     * @param idImmobile id immobile da eliminare
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @return
     */
    @PostMapping("/disable/{id_immobile}")
    public String getImmobileToDisable(@PathVariable("id_immobile") Long idImmobile, ModelMap model){
        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                // se l'utente è autenticato allora posso vedere i dati del singolo immobile
                int righeAggiornate = immobileService.immobileToDisable(idImmobile, authenticatedUser.get());
                if(righeAggiornate == 1){
                    // se tutto okay, allora torno alla pagina info utente
                    model.addAttribute("message", "Informazioni aggiornate con successo");
                    return "redirect:/site/utente/getInfo";
                }else{
                    model.addAttribute("error", "Errore nell'aggiornamento delle informazioni dell'utente");
                    return "Fail";
                }
            }else{
                model.addAttribute("error", "Bisogna esssere autenticati per aggiornare le informazioni");
                return "Login";
            }


        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }

    //

    /**
     * Permette di far visualizzare all'utente la lista di tutti gli immobili da lui caricati
     * @param offset indice di partenza della lista
     * @param pageSize grandezza pagina
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @return la lista degli immobili trovati
     */
    @GetMapping("/getImmobiliUtente/{offset}/{pageSize}")
    public String getListaImmobiliUtente(@PathVariable("offset") Long offset,
                                         @PathVariable("pageSize") @Min(1) @Max(20) Long pageSize,
                                         ModelMap model){
        try{
            // controllo se l'utente è autenticato
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                // se autenticato prelevo la lista degli immobili di quell'utente
                List<GetUtenteImmobiliDTO> foundedImmobili = immobileService.getUtenteListaImmobili(offset, pageSize,
                        authenticatedUser.get());
                if(!foundedImmobili.isEmpty()){
                    // se non vuota torno la lista
                    model.addAttribute("listaImmobili", foundedImmobili);
                    return "Utente";
                }else{
                    // altrimenti torno la lista vuota
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

    /**
     * Permette di prendere le informazioni dal database senza riempire troppo la memoria heap di Java tramite paginazione
     * @param offset indice iniziale della paginazine
     * @param pageSize numero di elementi da prendere
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @return una stringa con la view contenente la lista degli immobili
     */
    @GetMapping("/list/{offset}/{pageSize}")
    public String getListImmobili(@PathVariable("offset") Long offset,
                                  @PathVariable("pageSize") Long pageSize,
                                  ModelMap model){
        try{
            Optional<Utente> securedUser = authService.getAuthUtente();
            if(securedUser.isPresent()){
                // prendo la lista degli immobili da mettere nella home
                List<HomeImmobileDTO> foundedImmobili =
                        immobileService.getAllImmobiliPaginated(securedUser.get().getIdUtente(),offset, pageSize);
                if(!foundedImmobili.isEmpty()){
                    // se non vuota la ritorno
                    model.addAttribute("listImmobili", foundedImmobili);
                    return "Home";
                }else{
                    // altrimetni torno una lista vuota
                    model.addAttribute("message", "");
                    model.addAttribute("listImmobili", List.of());
                    return "Home";
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


}
