package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.exception.OffertaException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
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
@RequestMapping("/site/offerta")
@Validated
@AllArgsConstructor
public class OffertaController {

    private final OffertaService offertaService;
    private final OffertaUtenteService offertaUtenteService;
    private final AuthService authService;

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
                                return "inserisci_pagina_html_peppe";
                            }else{
                                model.addAttribute("error", "2 - Errore nella creazione di un offerta");
                                return "inserisci_pagina_html_peppe";
                            }

                        }else {
                            model.addAttribute("error", "1 - Errore nella creazione di un offerta");
                            return "inserisci_pagina_html_peppe";
                        }

                    } else {
                        model.addAttribute("error", "Immobile non trovato");
                        return "inserisci_pagina_html_peppe";
                    }
                } else {
                    model.addAttribute("error", "Proprietario non trovato");
                    return "inserisci_pagina_html_peppe";
                }

            } else {
                model.addAttribute("error", "Bisogna essere loggati per quest'azione");
                return "inserisci_pagina_html_peppe";
            }
        } catch (Exception error) {
            model.addAttribute("error", error.getMessage());
            return "inserisci_pagina_html_peppe";
        }
    }

    @PutMapping("/accept/{id_proposta}")
    public String acceptOfferta(@PathVariable("id_proposta") Long idOfferta,
                                ModelMap model){

        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()) {
                Optional<OfferteUtente> acceptedOfferta = offertaUtenteService.acceptOfferta(idOfferta, authenticatedUser.get());

                if(acceptedOfferta.isPresent()){
                    model.addAttribute("error", "Congratulazioni, hai accettato l'offerta");
                    return "inserisci_pagina_html_peppe";
                }else{
                    model.addAttribute("error", "Errore nell'accettazione dell'offerta");
                    return "inserisci_pagina_html_peppe";
                }

            }else{

                model.addAttribute("error", "Bisogna essere loggati per quest'azione");
                return "inserisci_pagina_html_peppe";
            }

        }catch (UtenteException | OffertaException | OffertaUtenteException error){
            model.addAttribute("error", error.getMessage());
            return "inserisci_pagina_html_peppe";
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
                    return "inserisci_pagina_html_peppe";
                }else{
                    model.addAttribute("error", "Errore nel rifiuto dell'offerta");
                    return "inserisci_pagina_html_peppe";
                }

            }else{

                model.addAttribute("error", "Bisogna essere loggati per quest'azione");
                return "inserisci_pagina_html_peppe";
            }

        }catch (UtenteException | OffertaException | OffertaUtenteException error){
            model.addAttribute("error", error.getMessage());
            return "inserisci_pagina_html_peppe";
        }


    }



}
