package com.application.virgo.controller;

import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.PdfGeneratorService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Permette di generare il pdf
 */
@Controller
@RequestMapping(path="/site/pdf")
@Validated
@AllArgsConstructor
public class PdfGeneratorContoller {

    private final PdfGeneratorService pdfGeneratorService;
    private final AuthService authService;

    /**
     * Permette di generare il pdf di un contratto
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @param idContratto id contratto da generae
     * @param response la risposta http
     * @return nulla, ma scarica il pdf
     */
    @GetMapping("/generate/{id_contratto}")
    public String exportPDF(ModelMap model, @PathVariable("id_contratto") Long idContratto, HttpServletResponse response){

        try{

            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                // se l'utente è autenticato genero il pdf e ritorno alla pagina della view
                return "redirect:/site/utente/getListaContratti/contratto/" +
                        pdfGeneratorService.exportPDF(authUser.get(), idContratto, response);
            }else{
                model.addAttribute("error", "Bisogna essere loggati per scaricare un contratto");
                return "Fail";
            }

        }catch (Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }

}
