package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.service.interfaces.UtenteService;
import com.application.virgo.wrapperclass.SecuredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
* Classe controller che si occupa di gestire tutte le richieste legate ad un utente
* Al suo interno ci sono tutti i metodi che servono per gestire l'aggiornamento di utente, la visualizzazione delle caht, contratti ed altro
* */
@Controller
@RequestMapping("/site/utente")
@Validated
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    @PutMapping("/updateData/{id_utente}")
    public String updateUtenteInformation(@PathVariable("id_utente") Long idUtenteDaModificare,
                                          @ModelAttribute UtenteDTO updatedUtente,
                                          ModelMap model){
        try{
            utenteService.updateUtenteInfoById(idUtenteDaModificare, updatedUtente);
            model.addAttribute("message", "Utente aggiornato correttamente");
            return "inserisci_pagina_html_peppe";
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "inserisci_pagina_html_peppe";
        }

    }

    @GetMapping("/getInfo/{id_utente}")
    public String getUsernameInformation(@PathVariable("id_utente")String idUtente, ModelMap model ){
        try{
            Optional<UtenteDTO> utenteInfo = utenteService.getUtenteById(Long.parseLong(idUtente));
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
}
