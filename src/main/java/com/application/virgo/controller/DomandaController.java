package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.inputDTO.RispostaDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.RispostaException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Risposta;
import com.application.virgo.service.interfaces.DomandaService;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.RispostaService;
import com.application.virgo.wrapperclass.SecuredUser;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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


    // metodo che permette di aggiungere una domanda relativa ad un immobile
    @PostMapping("/addQuestion/{id_immobile}")
    public ResponseEntity<String> addDomandaToImmobile(@ModelAttribute DomandaDTO tempNewDomandaDTO,
                                                       @PathVariable("id_immobile") Long idImmobile,
                                                       @AuthenticationPrincipal SecuredUser authenticatedUser){

        try{
            if(authenticatedUser != null){
                Optional<Domanda> addedDomanda = domandaService.addNewDomanda(tempNewDomandaDTO, authenticatedUser.getUtenteInformation());
                if(addedDomanda.isPresent()){
                    Optional<Immobile> newImmobile = immobileService.addNewDomandaToImmobile(addedDomanda.get(),
                            authenticatedUser.getUtenteInformation(), idImmobile);
                    if(newImmobile.isPresent()){
                        return new ResponseEntity<>("Domanda pubblicata correttamente", HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>("Errore nella pubblicazione di una nuova domanda", HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity<>("Impossibile creare la domanda", HttpStatus.BAD_REQUEST);
                }

            }else{
                return new ResponseEntity<>("Effettuare il login per creare inserire una domanda", HttpStatus.UNAUTHORIZED);
            }

        }catch (UtenteException error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.UNAUTHORIZED);
        }        catch (ImmobileException error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }

    }

    // metodo che permette di aggiungere una risposta ad una domanda
    @PostMapping("/reply/{id_domanda}/{id_immobile}")
    public ResponseEntity<String> addRispostaToDomanda(@ModelAttribute RispostaDTO tempNewRispostaDTO,
                                                       @PathVariable("id_domanda") Long idDomanda,
                                                       @PathVariable("id_immobile") Long idImmobile,
                                                       @AuthenticationPrincipal SecuredUser authenticatedUser){

        try{
            if(authenticatedUser != null){
                Optional<Risposta> addedRisposta = rispostaService.addNewRisposta(tempNewRispostaDTO, idDomanda,
                        authenticatedUser.getUtenteInformation(), idImmobile);
                if(addedRisposta.isPresent()){

                    Optional<Domanda> newDomandaConRisposta = domandaService.replyToDomanda(addedRisposta.get(),
                            idDomanda);

                    if(newDomandaConRisposta.isPresent()){
                        return new ResponseEntity<>("Risposta pubblicata correttamente", HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>("Errore nella pubblicazione della risposta", HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity<>("Errore nell'inserimento della risposta", HttpStatus.BAD_REQUEST);
                }

            }else{
                return new ResponseEntity<>("Effettuare il login per rispondere alla domanda", HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception error){
            return new ResponseEntity<>(error.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }


    }

}
