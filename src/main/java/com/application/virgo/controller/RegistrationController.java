package com.application.virgo.controller;


import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.EmailSenderService;
import com.application.virgo.service.interfaces.UtenteService;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/registration")
@Validated
@AllArgsConstructor
public class RegistrationController {

    private final UtenteService utenteService;
    private final EmailSenderService emailService;

    @GetMapping
    public String get() {
        //model.addAttribute("newUtente", newUtente);
        return "Registrazione";
    }

    @PostMapping()
    public String postActionRegister(@ModelAttribute UtenteDTO newUtente) {

        try {
            System.out.print("Prima del service");
            Optional<Utente> registeredUtente = utenteService.tryRegistrationHandler(newUtente);
            emailService.sendWelcomeMail(registeredUtente.get().getEmail(),registeredUtente.get().getNome(),registeredUtente.get().getCognome());

            return "redirect:/login";
        } catch (Exception e) {
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
