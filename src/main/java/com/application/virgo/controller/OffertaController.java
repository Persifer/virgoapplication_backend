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
import lombok.AllArgsConstructor;
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

    @GetMapping("/goToRilancio/{id_controparte}/{id_immobile}/{madeByProp}")
    public String getToInsertRilancio(ModelMap model, @PathVariable("id_controparte") Long idProprietario,
                                      @PathVariable("id_immobile") Long idImmobile,
                                      @PathVariable("madeByProp") Boolean madeByProprietario){ // madeByProprietario deve essere un booleano


        model.addAttribute("tempOffertaDTO", new InsertOffertaDTO());
        model.addAttribute("idProprietario", idProprietario);
        model.addAttribute("idImmobile", idImmobile);
        model.addAttribute("inviatoDaProprietario", madeByProprietario);

        return "Rilancio";
    }

    @PostMapping("/rilancia/{madeByProp}")
    public String rilanciaOfferta(@PathVariable("madeByProp") Boolean madeByProprietario,
                                  @ModelAttribute InsertOffertaDTO tempOffertaDTO,
                                  ModelMap model){
        try {
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                if (tempOffertaDTO.getIdProprietario() != null) {
                    if (tempOffertaDTO.getIdImmobile() != null) {
                        Optional<Offerta> newOfferta = offertaService.createNewOfferta(tempOffertaDTO);
                        if(newOfferta.isPresent()){
                            // qui made by proprietario è significativo e mi permette di capire chi sta facendo l'offerta
                            // io passo, in ordine: utenteAutenticato, nuovaOfferta, idControparte, madeByProp
                            Optional<OfferteUtente> newOffertaToUtente =
                                    offertaUtenteService.rilanciaOffertaToUtente(authenticatedUser.get(),
                                            newOfferta.get(), tempOffertaDTO.getIdProprietario(),
                                            madeByProprietario ? Boolean.TRUE : Boolean.FALSE);
                            if(newOffertaToUtente.isPresent()){
                                model.addAttribute("message", "offerta creata correttamente");
                                model.addAttribute("newOffertaToUtente", newOffertaToUtente.get());

                                if(madeByProprietario){ // /getListaOfferte/storico/{id_utente}/{id_immobile}
                                    return "redirect:/site/utente/getListaOfferte/storico/"
                                            +newOffertaToUtente.get().getProprietario().getIdUtente()+"/"
                                            +newOffertaToUtente.get().getOffertaInteressata().getIdImmobileInteressato().getIdImmobile();
                                }else{
                                    ///getListProposte/storico/{idOfferente}/{idImmobile}
                                    return "redirect:/site/utente/getListProposte/storico/"
                                            +newOffertaToUtente.get().getOfferente().getIdUtente()+"/"
                                            +newOffertaToUtente.get().getOffertaInteressata().getIdImmobileInteressato().getIdImmobile();
                                }
                            }else{
                                model.addAttribute("error", "2 - Errore nella creazione di un offerta");
                                return "Fail1";
                            }

                        }else {
                            model.addAttribute("error", "1 - Errore nella creazione di un offerta");
                            return "Fail2";
                        }

                    } else {
                        model.addAttribute("error", "Immobile non trovato");
                        return "Fail3";
                    }
                } else {
                    model.addAttribute("error", "Proprietario non trovato");
                    return "Fail4";
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
    @PostMapping("/accept/{id_proposta}/{id_immobile}/{isAcquirente}")
    public String acceptOfferta(@PathVariable("id_proposta") Long idOfferta,
                                @PathVariable("id_immobile") Long idImmobile,
                                @PathVariable("isAcquirente") Integer isAcquirente,
                                ModelMap model) {
        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                Optional<ContrattoUtente> acceptedOfferta = offertaUtenteService.acceptOfferta(idOfferta, authenticatedUser.get(), idImmobile);

                if(acceptedOfferta.isPresent()){
                    model.addAttribute("okmessage", "Congratulazioni, hai accettato l'offerta");

                    return "redirect:/site/utente/getInfo";

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
                Optional<OfferteUtente> declineOfferta = offertaUtenteService.declineOfferta(idOfferta, authenticatedUser.get());

                if(declineOfferta.isPresent()){
                    model.addAttribute("message", "Peccato, hai rifiutato l'offerta");

                    if(authenticatedUser.get().getIdUtente().equals(declineOfferta.get().getOfferente().getIdUtente())){
                        model.addAttribute("isAcquirente", "1");

                    }else{
                        model.addAttribute("isAcquirente", "0");
                    }


                    if(isAcquirente==1){ // /getListaOfferte/storico/{id_utente}/{id_immobile}
                        return "redirect:/site/utente/getListaOfferte/storico/"
                                +declineOfferta.get().getProprietario().getIdUtente()+"/"
                                +declineOfferta.get().getOffertaInteressata().getIdImmobileInteressato().getIdImmobile();
                    }else{
                        ///getListProposte/storico/{idOfferente}/{idImmobile}
                        return "redirect:/site/utente/getListProposte/storico/"
                                +declineOfferta.get().getOfferente().getIdUtente()+"/"
                                +declineOfferta.get().getOffertaInteressata().getIdImmobileInteressato().getIdImmobile();
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
            return "Fail";
        }


    }



}
