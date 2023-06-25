package com.application.virgo.controller;

import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.PdfGeneratorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path="/site/pdf")
@Validated
@AllArgsConstructor
public class PdfGeneratorContoller {

    private final PdfGeneratorService pdfGeneratorService;
    private final AuthService authService;

    @GetMapping("/generate/{id_contratto}")
    public String exportPDF(ModelMap model, Long idContratto){

        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            return "Ok";
        }catch (Exception error){
            model.addAttribute("error", error.getMessage());
            return "Fail";
        }
    }
}
