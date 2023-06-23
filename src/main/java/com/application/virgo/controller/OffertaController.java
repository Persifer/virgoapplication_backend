package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.exception.*;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.OffertaService;
import com.application.virgo.service.interfaces.OffertaUtenteService;
import com.application.virgo.wrapperclass.SecuredUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/site/offerte")
@Validated
@AllArgsConstructor
public class OffertaController {

    private final OffertaService offertaService;
    private final OffertaUtenteService offertaUtenteService;
    private final AuthService authService;
    @GetMapping
    public String get() {
        return "Offerte";
    }
    @PostMapping("/propose/{id_proprietario}/{id_immobile}")
    public String createProposta(@PathVariable("id_proprietario") Long idProprietario,
                                                  @PathVariable("id_immobile") Long idImmobile,
                                                  @ModelAttribute InsertOffertaDTO tempOffertaDTO,
                                                  ModelMap model) {
        try {
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                if (idProprietario != null) {
                    tempOffertaDTO.setIdProprietario(idProprietario);
                    if (idImmobile != null) {
                        tempOffertaDTO.setIdImmobile(idImmobile);
                        Optional<Offerta> newOfferta = offertaService.createNewOfferta(tempOffertaDTO);
                        if(newOfferta.isPresent()){
                            Optional<OfferteUtente> newOffertaToUtente =
                                    offertaUtenteService.saveOffertaToUtente(authenticatedUser.get(),
                                    newOfferta.get(), idProprietario);
                            if(newOffertaToUtente.isPresent()){
                                model.addAttribute("message", "offerta creata correttamente");
                                model.addAttribute("newOffertaToUtente", newOffertaToUtente.get());
                                return "SingolaOfferte";
                            }else{
                                model.addAttribute("error", "2 - Errore nella creazione di un offerta");
                                return "Fail";
                            }

                        }else {
                            model.addAttribute("error", "1 - Errore nella creazione di un offerta");
                            return "Fail";
                        }

                    } else {
                        model.addAttribute("error", "Immobile non trovato");
                        return "Fail";
                    }
                } else {
                    model.addAttribute("error", "Proprietario non trovato");
                    return "Fail";
                }

            } else {
                model.addAttribute("error", "Bisogna essere loggati per quest'azione");
                return "Login";
            }
        } catch (Exception error) {
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }

    @PutMapping("/rilancia/{id_proposta}")
    public String rilanciaOfferta(@PathVariable("id_proposta") Long idOfferta, ModelMap model){
        return "";
    }
    @PutMapping("/accept/{id_proposta}")
    public String acceptOfferta(@PathVariable("id_proposta") Long idOfferta,
                                ModelMap model) {
        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                Optional<ContrattoUtente> acceptedOfferta = offertaUtenteService.acceptOfferta(idOfferta, authenticatedUser.get());

                if(acceptedOfferta.isPresent()){
                    model.addAttribute("error", "Congratulazioni, hai accettato l'offerta");
                    return "Offerte";
                }else{
                    model.addAttribute("error", "Errore nell'accettazione dell'offerta");
                    return "Fail";
                }

            }else{

                model.addAttribute("error", "Bisogna essere loggati per quest'azione");
                return "Login";
            }

        }catch ( Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }


    }

    @PutMapping("/decline/{id_proposta}")
    public String declineOfferta(@PathVariable("id_proposta") Long idOfferta,
                                ModelMap model){

        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                Optional<OfferteUtente> acceptedOfferta = offertaUtenteService.declineOfferta(idOfferta, authenticatedUser.get());

                if(acceptedOfferta.isPresent()){
                    model.addAttribute("error", "Peccato, hai rifiutato l'offerta");
                    return "Offerte";
                }else{
                    model.addAttribute("error", "Errore nel rifiuto dell'offerta");
                    return "fail";
                }

            }else{

                model.addAttribute("error", "Bisogna essere loggati per quest'azione");
                return "login";
            }

        }catch (UtenteException | OffertaException | OffertaUtenteException error){
            model.addAttribute("error", error.getMessage());
            return "fail";
        }


    }



}
