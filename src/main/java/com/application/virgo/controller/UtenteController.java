package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.service.interfaces.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/*
* Classe controller che si occupa di gestire tutte le richieste legate ad un utente
* Al suo interno ci sono tutti i metodi che servono per gestire l'aggiornamento di utente, la visualizzazione delle caht, contratti ed altro
* */
@RestController
@RequestMapping("/site/utente")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    @PutMapping("/updateData/{id_utente}")
    public ResponseEntity<String> updateUtenteInformation(@PathVariable("id_utente") String idUtenteDaModificare,
                                                          @RequestBody UtenteDTO updatedUtente){
        try{
            utenteService.updateUtenteInfoById(Long.parseLong(idUtenteDaModificare), updatedUtente);
            return new ResponseEntity<String>("Utente aggiornato correttamente", HttpStatus.OK);
        }catch (UtenteException error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    @GetMapping("/getInfo/{id_utente}")
    public ResponseEntity<UtenteDTO> getUsernameInformation(@PathVariable("id_utente") String idUtente){
        try{
            Optional<UtenteDTO> utenteInfo = utenteService.getUtenteById(Long.parseLong(idUtente));
            if(utenteInfo.isPresent()){
                return new ResponseEntity<UtenteDTO>(utenteInfo.get(), HttpStatus.OK);
            }else{
                return new ResponseEntity<UtenteDTO>((UtenteDTO) null, HttpStatus.BAD_REQUEST);
            }

        }catch (UtenteException error){
            return new ResponseEntity<UtenteDTO>((UtenteDTO) null, HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<UtenteDTO>((UtenteDTO) null, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }
}
