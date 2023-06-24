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
    public String get(ModelMap model) {
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
                                return "redirect:/site/immobile/viewImmobile/"+idImmobile;
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

    @PostMapping("/rilancia/{id_proposta}/{madeByProp}")
    public String rilanciaOfferta(@PathVariable("id_proprietario") Long idProprietario,
                                  @PathVariable("id_immobile") Long idImmobile,
                                  @PathVariable("madeByProp") Integer madeByProprietario,
                                  @ModelAttribute InsertOffertaDTO tempOffertaDTO,
                                  ModelMap model){
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
                                    offertaUtenteService.rilanciaOffertaToUtente(authenticatedUser.get(),
                                            newOfferta.get(), idProprietario,
                                            madeByProprietario >= 1 ? Boolean.TRUE : Boolean.FALSE);
                            if(newOffertaToUtente.isPresent()){
                                model.addAttribute("message", "offerta creata correttamente");
                                model.addAttribute("newOffertaToUtente", newOffertaToUtente.get());
                                return "SingolaOfferta";
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
    @PostMapping("/accept/{id_proposta}/{isAcquirente}")
    public String acceptOfferta(@PathVariable("id_proposta") Long idOfferta,
                                @PathVariable("isAcquirente") Integer isAcquirente,
                                ModelMap model) {
        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                Optional<ContrattoUtente> acceptedOfferta = offertaUtenteService.acceptOfferta(idOfferta, authenticatedUser.get());

                if(acceptedOfferta.isPresent()){
                    model.addAttribute("error", "Congratulazioni, hai accettato l'offerta");

                    if(authenticatedUser.get().getIdUtente().equals(acceptedOfferta.get().getAcquirente().getIdUtente())){
                        model.addAttribute("isAcquirente", "1");

                    }else{
                        model.addAttribute("isAcquirente", "0");
                    }

                    if(isAcquirente==1){ // /getListaOfferte/storico/{id_utente}/{id_immobile}
                        return "redirect:/getListaOfferte/storico/"
                                +acceptedOfferta.get().getVenditore().getIdUtente()+"/"
                                +acceptedOfferta.get().getContrattoInteressato().getImmobileInteressato().getIdImmobile();
                    }else{
                        ///getListProposte/storico/{idOfferente}/{idImmobile}
                        return "redirect:/getListProposte/storico/"
                                +acceptedOfferta.get().getAcquirente().getIdUtente()+"/"
                                +acceptedOfferta.get().getContrattoInteressato().getImmobileInteressato().getIdImmobile();
                    }

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

    @PostMapping("/decline/{id_proposta}/{isAcquirente}")
    public String declineOfferta(@PathVariable("id_proposta") Long idOfferta,
                                 @PathVariable("isAcquirente") Integer isAcquirente,
                                ModelMap model){

        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                Optional<OfferteUtente> acceptedOfferta = offertaUtenteService.declineOfferta(idOfferta, authenticatedUser.get());

                if(acceptedOfferta.isPresent()){
                    model.addAttribute("message", "Peccato, hai rifiutato l'offerta");

                    if(authenticatedUser.get().getIdUtente().equals(acceptedOfferta.get().getOfferente().getIdUtente())){
                        model.addAttribute("isAcquirente", "1");

                    }else{
                        model.addAttribute("isAcquirente", "0");
                    }


                    if(isAcquirente==1){ // /getListaOfferte/storico/{id_utente}/{id_immobile}
                        return "redirect:/getListaOfferte/storico/"
                                +acceptedOfferta.get().getOfferente().getIdUtente()+"/"
                                +acceptedOfferta.get().getOffertaInteressata().getIdImmobileInteressato().getIdImmobile();
                    }else{
                        ///getListProposte/storico/{idOfferente}/{idImmobile}
                        return "redirect:/getListProposte/storico/"
                                +acceptedOfferta.get().getProprietario().getIdUtente()+"/"
                                +acceptedOfferta.get().getOffertaInteressata().getIdImmobileInteressato().getIdImmobile();
                    }
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
