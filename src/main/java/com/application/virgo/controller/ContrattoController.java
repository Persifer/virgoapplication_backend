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

/**
 * Permette di gestire gli endpoint che si occupano di generare i preventivi dei contratti stipulati tra clienti
 */
@Controller
@RequestMapping(path="/site/contratto")
@Validated
@AllArgsConstructor
public class ContrattoController {

    private AuthService authService;
    private CalcoloPreventiviService calcoloPreventiviService;

    /**
     * Metodo che fa da end-point per la generazione di un preventivo di un contratto
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @param idContratto idContratto su cui generare il preventivo
     * @param selettoreAzienda id dell'algoritmo da utilizzare
     * @return il valore del preventivo calcolato
     */
    @GetMapping("/calculate/{id_contratto}/{id_azienda}")
    public String calcolaPreventivo(ModelMap model, @PathVariable("id_contratto") Long idContratto,
                                    @PathVariable("id_azienda") Integer selettoreAzienda){
        try{
            // controlla che l'utente sia autenticato
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                // se autenticato procedo a calcolare il prezzo del preventivo
                Double prezzoContratto = calcoloPreventiviService.calcolaPreventivoImmobile(idContratto, selettoreAzienda);
                // lo aggiungo alla model
                model.addAttribute("risultato", prezzoContratto);
                // ritorno
                return "redirect:/site/utente/getListaContratti/contratto/"+idContratto;

            }else{
                // entro se l'utente non è autenticato
                model.addAttribute("error", "Bisogna essere loggati per ");
                return "Fail";
            }
        }catch (Exception error){
            // entro in caso di errore
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }


}
