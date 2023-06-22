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

@Controller
@RequestMapping(path="/site/immobile")
@Validated
@AllArgsConstructor
public class DomandaController {

    private final DomandaService domandaService;
    private final ImmobileService immobileService;
    private final RispostaService rispostaService;
    private final AuthService authService;


    // metodo che permette di aggiungere una domanda relativa ad un immobile
    @PostMapping("/addQuestion/{id_immobile}")
    public String addDomandaToImmobile(@ModelAttribute DomandaDTO tempNewDomandaDTO,
                                                       @PathVariable("id_immobile") Long idImmobile, ModelMap model){

        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()){
                Optional<Domanda> addedDomanda = domandaService.addNewDomanda(tempNewDomandaDTO, authenticatedUser.get());
                if(addedDomanda.isPresent()){
                    Optional<GetImmobileInfoDTO> newImmobile = immobileService.addNewDomandaToImmobile(addedDomanda.get(),
                            authenticatedUser.get(), idImmobile);
                    if(newImmobile.isPresent()){
                        model.addAttribute("message", "Domanda inserita con successo");
                        model.addAttribute("wantedImmobile", newImmobile.get());
                        return "Immobile";
                    }else{
                        model.addAttribute("error", "L'immobile selezionato non esiste");
                        return "Fail";
                    }
                }else{
                    model.addAttribute("error", "Problemi con la creazione della domanda");
                    return "Fail";
                }

            }else{
                model.addAttribute("message", "Bisogna essere autenticati per inserire una domanda");
                return "Fail";
            }

        }catch ( Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }

    }

    // metodo che permette di aggiungere una risposta ad una domanda
    @PostMapping("/reply/{id_domanda}/{id_immobile}")
    public String addRispostaToDomanda(@ModelAttribute RispostaDTO tempNewRispostaDTO,
                                                       @PathVariable("id_domanda") Long idDomanda,
                                                       @PathVariable("id_immobile") Long idImmobile,
                                                       ModelMap model){

        try{
            Optional<Utente> authenticatedUser = authService.getAuthUtente();
            if(authenticatedUser.isPresent()){
                Optional<Risposta> addedRisposta = rispostaService.addNewRisposta(tempNewRispostaDTO, idDomanda,
                        authenticatedUser.get(), idImmobile);
                if(addedRisposta.isPresent()){

                    Optional<Domanda> newDomandaConRisposta = domandaService.replyToDomanda(addedRisposta.get(),
                            idDomanda);

                    if(newDomandaConRisposta.isPresent()){
                        model.addAttribute("message", "Risposta inserita con successo");
                        return "inserisci_pagina_html_peppe";
                    }else{
                        model.addAttribute("error", " 2 - Errore nell'inserimento della risposta");
                        return "inserisci_pagina_html_peppe";
                    }
                }else{
                    model.addAttribute("error", "1 - Errore inserimento della risposta");
                    return "inserisci_pagina_html_peppe";
                }

            }else{
                model.addAttribute("error", "Bisogna essere autorizzati per inserire una risposta");
                return "inserisci_pagina_html_peppe";
            }

        }catch (Exception error){
            model.addAttribute("error", error.getMessage());
            return "inserisci_pagina_html_peppe";
        }


    }

}
