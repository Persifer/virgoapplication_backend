package com.application.virgo.controller;


import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.UtenteService;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
@Validated
public class RegistrationController {

    private final UtenteService utenteService;

    public RegistrationController(UtenteService utenteService) {
        this.utenteService = utenteService;
    }

    @GetMapping
    public String get() {
        //model.addAttribute("newUtente", newUtente);
        return "Registrazione";
    }

    @PostMapping()
    public String postActionRegister(@ModelAttribute UtenteDTO newUtente) {
        System.out.print("Dentro l'handler \n\t=> "+ newUtente.toString());
        try {
            System.out.print("Prima del service");
            utenteService.tryRegistrationHandler(newUtente);
            return "redirect:/login";
        } catch (UtenteException e) {
            e.printStackTrace();
            return "redirect:/registration";
        }
    }


    /*@PostMapping
    public ResponseEntity<String> registration(@RequestBody Utente newUtente){
        System.out.print("Dentro Registration controller");
        try{
            utenteService.registrationHandler(newUtente);
            return new ResponseEntity<>("[*] Utente registrato correttamente [*] ", HttpStatus.OK);
        }catch (UtenteException error){

            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){

            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

}
