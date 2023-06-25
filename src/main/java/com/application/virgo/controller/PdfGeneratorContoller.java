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
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping(path="/site/pdf")
@Validated
@AllArgsConstructor
public class PdfGeneratorContoller {

    private final PdfGeneratorService pdfGeneratorService;
    private final AuthService authService;

    @GetMapping("/generate/{id_contratto}")
    public String exportPDF(ModelMap model, Long idContratto, HttpServletResponse response){

        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                response.setContentType("application/pdf");
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");

                String currentDateTime = dateFormatter.format(new Date());
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
                response.setHeader(headerKey, headerValue);
                pdfGeneratorService.exportPDF(authUser.get(), idContratto, response);



                return "Page";
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
