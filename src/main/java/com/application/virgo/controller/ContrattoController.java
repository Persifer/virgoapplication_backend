package com.application.virgo.controller;

import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.CalcoloPreventiviService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path="/site/contratto")
@Validated
@AllArgsConstructor
public class ContrattoController {

    private AuthService authService;
    private CalcoloPreventiviService calcoloPreventiviService;

    @GetMapping("/calculate/{id_contratto}/{id_azienda}")
    public String calcolaPreventivo(ModelMap model, @PathVariable("id_contratto") Long idContratto,
                                    @PathVariable("id_azienda") Integer selettoreAzienda){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                Double prezzoContratto = calcoloPreventiviService.calcolaPreventivoImmobile(idContratto, selettoreAzienda);
                System.out.println("prezzo contratto: " + prezzoContratto);
                model.addAttribute("risultato", prezzoContratto);
                return "redirect:/site/utente/getListaContratti/contratto/"+idContratto;

            }else{
                model.addAttribute("error", "Bisogna essere loggati per ");
                return "Fail";
            }
        }catch (Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }


}
