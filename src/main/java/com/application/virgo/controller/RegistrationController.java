package com.application.virgo.controller;

import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/registration")
public class RegistrationController {

    private final UtenteService utenteService;

    @GetMapping
    public String get() {
        return "Registrazione";
    }

    @PostMapping()
    public String postActionRegister(@ModelAttribute Utente newUtente) {
        try {
            utenteService.registrationHandler(newUtente);
            return "redirect:/login";
        } catch (UtenteException e) {
            e.printStackTrace();
            return "redirect:/registration";
        }
    }


    /*@PostMapping
    public ResponseEntity<String> registration(@RequestBody Utente newUtente){
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
