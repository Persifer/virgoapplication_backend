package com.application.virgo.controller;


import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.EmailSenderService;
import com.application.virgo.service.interfaces.UtenteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/registration")
@Validated
@AllArgsConstructor
public class RegistrationController {

    private final UtenteService utenteService;
    private final EmailSenderService emailService;

    /**
     * Preleva la view dalla registrazione
     * @return la view Registrazione
     */
    @GetMapping
    public String get() {
        //model.addAttribute("newUtente", newUtente);
        return "Registrazione";
    }

    /**
     * Metodo che richiama la business logic per la registrazione
     * @param newUtente dati utente
     * @return la login page
     */
    @PostMapping()
    public String postActionRegister(@ModelAttribute @Valid UtenteDTO newUtente) {

        try {

            Optional<Utente> registeredUtente = utenteService.tryRegistrationHandler(newUtente);
            emailService.sendWelcomeMail(registeredUtente.get().getEmail(),registeredUtente.get().getNome(),registeredUtente.get().getCognome());

            return "redirect:/login";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/registration";
        }
    }


}
