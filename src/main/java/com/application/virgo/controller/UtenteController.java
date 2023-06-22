package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.*;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
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
    private ContrattoUtenteService contrattoUtenteService;

    @GetMapping
    public String get() {
        // lista di immobili
        //
        return "Utente";
    }

    @PutMapping("/updateData")
    public String updateUtenteInformation(@PathVariable("id_utente") Long idUtenteDaModificare,
                                          @ModelAttribute UtenteDTO updatedUtente,
                                          ModelMap model){


        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                utenteService.updateUtenteInfoById(idUtenteDaModificare, updatedUtente);
                model.addAttribute("message", "Utente aggiornato correttamente");
                return "Utente";
            }else{
                model.addAttribute("error", "Bisogna essere autenticati per modificare i dati!");
                return "Login";
            }

        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }

    }

    @GetMapping("/getInfo")
    public String getUsernameInformation(ModelMap model ){
        try{
            Optional<Utente> utenteInfo = authService.getAuthUtente();
            if(utenteInfo.isPresent()){
                model.addAttribute("utente", utenteInfo.get());
                return "Utente";
            }else{
                model.addAttribute("error", "Utente non trovato");
                return "Fail";
            }

        }catch (UtenteException error){
            model.addAttribute("error", "Utente non trovato");
            return "Fail";
        }
    }

    @GetMapping("/getListProposte")
    public String getOfferteRicevute(ModelMap model){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewListaOfferteDTO> listaProposte = utenteService.getListaProposte(authUser.get());
                model.addAttribute("listaProposte", listaProposte);
                return "Proposte";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "Login";
            }
        }catch (UtenteException | OffertaUtenteException error){
            model.addAttribute("error", "Utente non trovato");
            return "Utente";
        }
    }

    @GetMapping("/getListProposte/storico/{idOfferente}/{idImmobile}")
    public String getOfferteBetweenUtenti(ModelMap model, @PathVariable("idOfferente") Long idOfferente, @PathVariable("idImmobile") Long idImmobile){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewOfferteBetweenUtentiDTO> listaOfferte =
                        utenteService.getAllProposteBetweenUtenti(authUser.get(), idOfferente, idImmobile);
                model.addAttribute("listaOfferte", listaOfferte);
                return "SingolaProposta";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "Login";
            }
        }catch (UtenteException | ImmobileException error){
            model.addAttribute("error", "Utente non trovato");
            return "Login";
        }
    }

    @GetMapping("/getListaOfferte")
    public String getOfferte(ModelMap model){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewListaOfferteDTO> listaOfferte = utenteService.getListaOfferte(authUser.get());
                model.addAttribute("listaOfferte", listaOfferte);
                return "Offerte";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "Login";
            }
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (OffertaUtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }
    }

    @GetMapping("/getListaOfferte/storico/{id_utente}/{id_immobile}")
    public String getStoricoOfferte(ModelMap model,
                                    @PathVariable("id_utente") Long idUtente,
                                    @PathVariable("id_immobile") Long idImmobile){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewOfferteBetweenUtentiDTO> listaOfferte =
                        utenteService.getAllOfferteBetweenUtenti(authUser.get(), idUtente, idImmobile);
                model.addAttribute("listaOfferte", listaOfferte);
                return "Ciao";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "inserisci_pagina_html_peppe";
            }
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (ImmobileException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }
    }

    @GetMapping("/getListaContratti/{offset}/{pageSize}")
    public String getListaContratti(ModelMap model, @PathVariable("offset") Long offset, @PathVariable("pageSize") Long pageSize){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ContrattiUtenteDTO> listContrattiUtente = contrattoUtenteService.getListaContrattiForUtente(authUser.get(),
                        offset, pageSize);
                model.addAttribute("listaContratti", listContrattiUtente);
                return "Offerte";
            }else{
                model.addAttribute("error", "Utente non trovato");
                return "inserisci_pagina_html_peppe";
            }
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (ContrattoUtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }
    }

    @GetMapping("/getListaContratti/contratto/{id_contratto}")
    public String getSingleContratto(ModelMap model, @PathVariable("id_contratto") Long idContratto){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                Optional<DettagliContrattoDTO> listContrattiUtente = contrattoUtenteService.getDettagliContratto(authUser.get(),
                        idContratto);
                model.addAttribute("listaContratti", listContrattiUtente);
                return "SingolaProposta";
            }else{
                model.addAttribute("error", "Utente non trovato");
                return "Login";
            }
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (ContrattoUtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }
    }
}
