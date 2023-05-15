package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.LoginUtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.service.interfaces.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.application.virgo.utilities.Constants.CONTROLLER_OUTPUT;

@Controller
@RequestMapping(path = "/login")
public class LoginController {

    @GetMapping
    public String get() {
        return "Login";
    }

   /* @GetMapping
=======
    /* @GetMapping
>>>>>>> security
    public ResponseEntity<String> login(@RequestBody LoginUtenteDTO newUtente){
        try{
            utenteService.loginHandler(newUtente);
            return new ResponseEntity<String>("[*] Utente loggato correttamente [*] ", HttpStatus.OK);
        }catch (UtenteException error){

            return new ResponseEntity<String>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){

            return new ResponseEntity<String>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
<<<<<<< HEAD
    }*/

}
