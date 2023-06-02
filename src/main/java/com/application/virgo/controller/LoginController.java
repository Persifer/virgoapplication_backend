package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.LoginUtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.service.interfaces.UtenteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import static com.application.virgo.utilities.Constants.CONTROLLER_OUTPUT;

@Controller
@RequestMapping(path = "/login")
@Validated
public class LoginController {


    @GetMapping
    public String get() {
        return "Login";
    }

    @GetMapping("/success")
    public String getSuccess() {
        return "redirect:/vai";
    }

}
