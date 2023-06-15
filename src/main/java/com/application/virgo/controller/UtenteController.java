package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.ViewListaOfferteDTO;
import com.application.virgo.DTO.outputDTO.ViewOfferteBetweenUtentiDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/*
* Classe controller che si occupa di gestire tutte le richieste legate ad un utente
* Al suo interno ci sono tutti i metodi che servono per gestire l'aggiornamento di utente, la visualizzazione delle caht, contratti ed altro
* */
@Controller
@RequestMapping("/site/utente")
@AllArgsConstructor
@Validated
public class UtenteController {


    private UtenteService utenteService;
    private AuthService authService;
    @PutMapping("/updateData")
    public String updateUtenteInformation(@PathVariable("id_utente") Long idUtenteDaModificare,
                                          @ModelAttribute UtenteDTO updatedUtente,
                                          ModelMap model){


        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                utenteService.updateUtenteInfoById(idUtenteDaModificare, updatedUtente);
                model.addAttribute("message", "Utente aggiornato correttamente");
                return "inserisci_pagina_html_peppe";
            }else{
                model.addAttribute("error", "Bisogna essere autenticati per modificare i dati!");
                return "inserisci_pagina_html_peppe";
            }

        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "inserisci_pagina_html_peppe";
        }

    }

    @GetMapping("/getInfo/{id_utente}")
    public String getUsernameInformation(@PathVariable("id_utente")Long idUtente, ModelMap model ){
        try{
            Optional<UtenteDTO> utenteInfo = utenteService.getUtenteById(idUtente);
            if(utenteInfo.isPresent()){
                model.addAttribute("utente", utenteInfo.get());
                return "inserisci_pagina_html_peppe";
            }else{
                model.addAttribute("error", "Utente non trovato");
                return "inserisci_pagina_html_peppe";
            }

        }catch (UtenteException error){
            model.addAttribute("error", "Utente non trovato");
            return "inserisci_pagina_html_peppe";
        }
    }

    @GetMapping("/getListProposte/{offset}/{pageSize}/")
    public String getOfferteRicevute(ModelMap model, @PathVariable("offset") Long offset, @PathVariable("pageSize") Long pageSize){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewListaOfferteDTO> listaOfferte = utenteService.getListaProposte(authUser.get(), offset, pageSize);
                model.addAttribute("listaOfferte", listaOfferte);
                return "Ciao";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "inserisci_pagina_html_peppe";
            }
        }catch (UtenteException | OffertaUtenteException error){
            model.addAttribute("error", "Utente non trovato");
            return "inserisci_pagina_html_peppe";
        }
    }

    @GetMapping("/getListProposte/storico/{idUtente}/{idImmobile}")
    public String getOfferteBetweenUtenti(ModelMap model, @PathVariable("idUtente") Long idOfferente, @PathVariable("idImmobile") Long idImmobile){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewOfferteBetweenUtentiDTO> listaOfferte = utenteService.getAllProposteBetweenUtenti(authUser.get(), idOfferente, idImmobile);
                model.addAttribute("listaOfferte", listaOfferte);
                return "Ciao";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "inserisci_pagina_html_peppe";
            }
        }catch (UtenteException | ImmobileException error){
            model.addAttribute("error", "Utente non trovato");
            return "inserisci_pagina_html_peppe";
        }
    }

    @GetMapping("/getListaOfferte/{offset}/{pageSize}")
    public String getOfferte(ModelMap model, @PathVariable("offset") Long offset, @PathVariable("pageSize") Long pageSize){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewListaOfferteDTO> listaOfferte = utenteService.getListaOfferte(authUser.get(), offset, pageSize);
                model.addAttribute("listaOfferte", listaOfferte);
                return "Ciao";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "inserisci_pagina_html_peppe";
            }
        }catch (UtenteException | OffertaUtenteException error){
            model.addAttribute("error", "Utente non trovato");
            return "inserisci_pagina_html_peppe";
        }
    }

    @GetMapping("/getListaOfferte/storico/{id_utente}/{id_immobile}")
    public String getStoricoOfferte(ModelMap model,
                                    @PathVariable("id_utente") Long idUtente,
                                    @PathVariable("id_immobile") Long idImmobile){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewListaOfferteDTO> listaOfferte = utenteService.getListaOfferte(authUser.get(), idUtente, idImmobile);
                model.addAttribute("listaOfferte", listaOfferte);
                return "Ciao";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "inserisci_pagina_html_peppe";
            }
        }catch (UtenteException | OffertaUtenteException error){
            model.addAttribute("error", "Utente non trovato");
            return "inserisci_pagina_html_peppe";
        }
    }

}
