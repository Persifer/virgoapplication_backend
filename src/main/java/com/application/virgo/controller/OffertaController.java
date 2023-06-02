package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Offerta;
import com.application.virgo.service.interfaces.OffertaService;
import com.application.virgo.service.interfaces.OffertaUtenteService;
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
@RequestMapping("/site/offerta")
@Validated
@AllArgsConstructor
public class OffertaController {

    private final OffertaService offertaService;
    private final OffertaUtenteService offertaUtenteService;

    @PostMapping("/propose/{id_proprietario}/{id_immobile}")
    public ResponseEntity<String> createAProposta(@PathVariable("id_proprietario") Long idProprietario,
                                                  @PathVariable("id_immobile") Long idImmobile,
                                                  @ModelAttribute InsertOffertaDTO tempOffertaDTO,
                                                  @AuthenticationPrincipal SecuredUser authenticatedUtente) {
        try {
            if (authenticatedUtente != null) {
                if (idProprietario != null) {
                    tempOffertaDTO.setIdProprietario(idProprietario);
                    if (idImmobile != null) {
                        tempOffertaDTO.setIdImmobile(idImmobile);

                        Optional<Offerta> newOfferta = offertaService.createNewOfferta(tempOffertaDTO);
                        if(newOfferta.isPresent()){
                            Optional<OfferteUtente> newOffertaToUtente = offertaUtenteService.saveOffertaToUtente(authenticatedUtente.getUtenteInformation(),
                                    newOfferta.get(), idProprietario);

                            if(newOffertaToUtente.isPresent()){
                                return new ResponseEntity<>("Offerta creata correttamente!", HttpStatus.OK);
                            }else{
                                return new ResponseEntity<>("Problemi con l'assegnazione di una nuova offerta", HttpStatus.BAD_REQUEST);
                            }

                        }else {
                            return new ResponseEntity<>("Problemi con la crezione di una nuova offerta", HttpStatus.BAD_REQUEST);
                        }

                    } else {
                        return new ResponseEntity<>("Bisogna avere un immobile per cui fare un'offerta", HttpStatus.BAD_REQUEST);
                    }
                } else {
                    return new ResponseEntity<>("Bisogna avere un proprietario a cui effettuare l'offerta", HttpStatus.BAD_REQUEST);
                }

            } else {
                return new ResponseEntity<>("Bisogna essere loggati per proporre un'offerta", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
