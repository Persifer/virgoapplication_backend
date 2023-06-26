package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.inputDTO.RispostaDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.RispostaException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Risposta;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.DomandaService;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.RispostaService;
import com.application.virgo.wrapperclass.SecuredUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

/**
 * Controller che si occupa di gestire ed esporre gli end-point che gestiscono la creazione e visualizzazione
 * delle domande e delle risposte alle domande
 */
@Controller
@RequestMapping(path="/site/immobile")
@Validated
@AllArgsConstructor
public class DomandaController {

    private final DomandaService domandaService;
    private final ImmobileService immobileService;
    private final RispostaService rispostaService;
    private final AuthService authService;



    /**
     * Metodo che permette di aggiungere una domanda relativa ad un immobile
     * @param tempNewDomandaDTO i dati della domanda da inserire per l'immobile
     * @param idImmobile l'id immobile a cui inserire la domanda
     * @param model l'istanza della classe model che permette di passare i parametri al front-end
     * @return una view che ridireziona in base al risultato ricevuto dal back-end
     */
    @PostMapping("/addQuestion/{id_immobile}")
    public String addDomandaToImmobile(@ModelAttribute DomandaDTO tempNewDomandaDTO,
                                                       @PathVariable("id_immobile") Long idImmobile, ModelMap model){

        try{
            // controllo che l'utente i aautenticato
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()){
                // prelevo la domanda inserita dalla business-logic
                Optional<Domanda> addedDomanda = domandaService.addNewDomanda(tempNewDomandaDTO, authenticatedUser.get(), idImmobile);
                if(addedDomanda.isPresent()){
                    // ritorno
                    return "redirect:/site/immobile/viewImmobile/"+addedDomanda.get().getImmobileInteressato().getIdImmobile();
                }else{
                    // entro se ci osno errori nel salvataggio
                    model.addAttribute("error", "Problemi con la creazione della domanda");
                    return "Fail";
                }

            }else{
                //entro se l'utente non è autenticato
                model.addAttribute("error", "Bisogna essere autenticati per inserire una domanda");
                return "Login";
            }

        }catch ( Exception error){
            // entro in caso di errore
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }

    }


    /**
     * Metodo che permette di disabilitare una domanda da parte del proprietario di un immobile
     * @param idDomanda id domanda da disabilitare
     * @param model l'istanza della classe model che permette di passare i parametri al front-end
     * @return una view che ridireziona in base al risultato ricevuto dal back-end
     */
    @PostMapping("/disableQuestion/{id_domanda}")
    public String disableDomandaOfImmobile(@PathVariable("id_domanda") Long idDomanda, ModelMap model){

        try{
            // controllo se l'utente è autenticato
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()){
                // prelevo la domanda modificata dalla business logic
                Optional<Domanda> modifiedDomanda = domandaService.disabilitaDomanda( authenticatedUser.get(), idDomanda);
                if(modifiedDomanda.isPresent()){
                    // se presente ritorno senza alcun problema alla pagina immobile
                    model.addAttribute("message", "Domanda inserita con successo");
                    return "redirect:/site/immobile/mioImmobile/"+modifiedDomanda.get().getImmobileInteressato().getIdImmobile();
                }else{
                    // vado alla pagina di errore
                    model.addAttribute("error", "Problemi con la creazione della domanda");
                    return "Fail";
                }

            }else{
                model.addAttribute("error", "Bisogna essere autenticati per inserire una domanda");
                return "Login";
            }

        }catch ( Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }

    }

    //

    /**
     * Metodo che permette di aggiungere una risposta ad una domanda
     * @param tempNewRispostaDTO i dati della risposta da aggiungere
     * @param idDomanda id domanda a cui rispondere
     * @param idImmobile id immobile a cui aggiungere la domanda
     * @param model l'istanza della classe model che permette di passare i parametri al front-end
     * @return una view che ridireziona in base al risultato ricevuto dal back-end
     */
    @PostMapping("/reply/{id_domanda}/{id_immobile}")
    public String addRispostaToDomanda(@ModelAttribute RispostaDTO tempNewRispostaDTO, @PathVariable("id_domanda") Long idDomanda,
                                                       @PathVariable("id_immobile") Long idImmobile, ModelMap model){

        try{
            // controllo che l'utente sia autenticato
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()){
                // ritorno la risposta aggiunta dalla business logic
                Optional<Risposta> addedRisposta = rispostaService.addNewRisposta(tempNewRispostaDTO, idDomanda,
                        authenticatedUser.get(), idImmobile);
                if(addedRisposta.isPresent()){
                    // se tutto okay allora ritorno alla pagina precedente
                    return "redirect:/site/immobile/mioImmobile/"+addedRisposta.get().getDomandaDiRiferimento().getImmobileInteressato().getIdImmobile();
                }else{
                    model.addAttribute("error", "1 - Errore inserimento della risposta");
                    return "Fail";
                }

            }else{
                model.addAttribute("error", "Bisogna essere autorizzati per inserire una risposta");
                return "Fail";
            }

        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (ImmobileException | DomandaException error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }



    }

}
