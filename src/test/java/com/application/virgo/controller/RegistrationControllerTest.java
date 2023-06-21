package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.EmailSenderService;
import com.application.virgo.service.interfaces.UtenteService;

import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class RegistrationControllerTest {

    @Mock
    private UtenteService utenteService;

    @Mock
    private EmailSenderService emailService;

    @InjectMocks
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        registrationController = new RegistrationController(utenteService, emailService);
    }

    @Test
    void get_returnsRegistrazioneView() {
        String viewName = registrationController.get();
        assertEquals("Registrazione", viewName);
    }

    @Test
    void postActionRegister_validInput_redirectsToLogin() throws MessagingException, UtenteException {
        UtenteDTO newUtente = new UtenteDTO();
        Utente registeredUtente = Utente.builder().email("mail@exmp.it").nome("giovanni").cognome("Gorberto").build();

        when(utenteService.tryRegistrationHandler(newUtente)).thenReturn(Optional.of(registeredUtente));

        String viewName = registrationController.postActionRegister(newUtente);

        verify(emailService).sendWelcomeMail(registeredUtente.getEmail(), registeredUtente.getNome(), registeredUtente.getCognome());
        assertEquals("redirect:/login", viewName);
    }

    @Test
    void postActionRegister_exceptionThrown_redirectsToRegistration() throws UtenteException {
        UtenteDTO newUtente = new UtenteDTO();

        when(utenteService.tryRegistrationHandler(newUtente)).thenThrow(new UtenteException("Utente non registrato"));

        String viewName = registrationController.postActionRegister(newUtente);

        assertEquals("redirect:/registration", viewName);
    }
}