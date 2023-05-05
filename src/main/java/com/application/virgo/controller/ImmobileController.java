package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import com.application.virgo.service.interfaces.ImmobileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.application.virgo.utilities.Constants.CONTROLLER_OUTPUT;

@RestController
@RequestMapping(path="/site/immobile", produces = CONTROLLER_OUTPUT)
public class ImmobileController {

    @Autowired
    private ImmobileService immobileService;

    // Mapper per la creazione di un nuovo immobile associato ad singolo utente proprietario
    @PostMapping("/addnew/{id_utente}")
    public ResponseEntity<String> createNewImmobile(@RequestBody ImmobileDTO tempNewImmobile,
                                                    @PathVariable("id_utente") String idProprietario){
        try{
            tempNewImmobile.setIdProprietario(Long.parseLong(idProprietario));

            Optional<ImmobileDTO> newImmobile = immobileService.createNewImmobile(tempNewImmobile);
            if(newImmobile.isPresent()){
                return new ResponseEntity<String>("Immobile registrato correttamente", HttpStatus.OK);
            }else{
                return new ResponseEntity<String>("Errore nella registrazione di un nuovo immobile", HttpStatus.BAD_REQUEST);
            }

        }catch (ImmobileException | UtenteException error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<String>(error.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    // Mapper che permette di reperire i dati di un singolo immobile associato ad un utente tramite l'uso di GetImmobileInfoDTO
    // il DTO
    @GetMapping("/viewImmobile/{id_immobile}")
    public ResponseEntity<GetImmobileInfoDTO> getImmobileInformation(@PathVariable("id_immobile") String idImmobile){
        try{

            Optional<GetImmobileInfoDTO> storedImmobile = immobileService.getImmobileById(idImmobile);
            if(storedImmobile.isPresent()){
                return new ResponseEntity<GetImmobileInfoDTO>(storedImmobile.get(), HttpStatus.OK);
            }else{
                return new ResponseEntity<GetImmobileInfoDTO>((GetImmobileInfoDTO) null, HttpStatus.BAD_REQUEST);
            }

        }catch (ImmobileException error){
            return new ResponseEntity<GetImmobileInfoDTO>((GetImmobileInfoDTO) null, HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<GetImmobileInfoDTO>((GetImmobileInfoDTO) null, HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

//    // TODO -> recupera dati utente dalla sessione
//    @PutMapping("/updateInfo/{id_immobile}")
//    public ResponseEntity<String> modifyImmobileInfo(@RequestBody ImmobileDTO tempUpdatedimmobileDTO){
//
//    }


}
