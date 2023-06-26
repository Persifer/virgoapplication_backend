package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.inputDTO.RispostaDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.exception.DomandaException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.RispostaException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Risposta;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.ui.ModelMap;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class DomandaControllerTest {


    @Mock
    private AuthService authService;
    @Mock
    private DomandaService domandaService;
    @Mock
    private RispostaService rispostaService;
    @Mock
    private ImmobileService immobileService;

    @InjectMocks
    private DomandaController domandaController;

    private ModelMap model;
    private Optional<Utente> authUser;
    private Immobile immobile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        domandaController = new DomandaController(domandaService, immobileService, rispostaService, authService );
        model = new ModelMap();

        authUser = Optional.of(
                Utente.builder()
                        .idUtente(1L)
                        .nome("antonio")
                        .cognome("giorgino")
                        .email("mail@mail.it")
                        .password("$2a$10$qwkYqMJwaQFb/B/V4YKq9Os/JUBUqZkwUdum5Ul2oEOP86pPHEFFm")
                        .build());

        immobile = Immobile.builder().build();
    }


    @Test
    void addDomandaToImmobile_autenticated_success() throws Exception {

        Optional<Domanda> domanda = Optional.of(new Domanda());
        Optional<GetImmobileInfoDTO> newImmobile = Optional.of(new GetImmobileInfoDTO());
        DomandaDTO domandaDTO = new DomandaDTO();
        Domanda newDomanda = new Domanda();

        when(authService.getAuthUtente()).thenReturn(authUser);

        when(domandaService.addNewDomanda(domandaDTO, authUser.get(),1L))
                .thenReturn(domanda);


        String viewName = domandaController.addDomandaToImmobile(domandaDTO, 1L, model);

        assertEquals("riuscito", viewName);
        assertTrue(model.containsAttribute("message"));

    }

    @Test
    void addDomandaToImmobile_unautenticated_not_success() throws Exception {

        Optional<Domanda> domanda = Optional.of(new Domanda());
        Optional<GetImmobileInfoDTO> newImmobile = Optional.of(new GetImmobileInfoDTO());
        DomandaDTO domandaDTO = new DomandaDTO();
        Domanda newDomanda = new Domanda();

        when(authService.getAuthUtente()).thenThrow(UtenteException.class);

        when(domandaService.addNewDomanda(domandaDTO, authUser.get(),1L))
                .thenThrow(UtenteException.class);


        String viewName = domandaController.addDomandaToImmobile(domandaDTO, 1L, model);

        assertEquals("Fail", viewName);
        assertTrue(model.containsAttribute("error"));

    }

    @Test
    void addDomandaToImmobile_autenticated_domanda_not_found() throws Exception {


        Optional<GetImmobileInfoDTO> newImmobile = Optional.of(new GetImmobileInfoDTO());
        DomandaDTO domandaDTO = new DomandaDTO();
        Domanda newDomanda = new Domanda();

        when(authService.getAuthUtente()).thenReturn(authUser);

        when(domandaService.addNewDomanda(domandaDTO, authUser.get(),1L))
                .thenReturn(Optional.empty());


        String viewName = domandaController.addDomandaToImmobile(domandaDTO, 1L, model);

        assertEquals("Fail", viewName);
        assertTrue(model.containsAttribute("error"));
    }


    @Test
    void addRispostaToDomanda_autenticated_success()
            throws UtenteException, ImmobileException, RispostaException, DomandaException {

        Optional<Risposta> risposta = Optional.of(new Risposta());
        Optional<GetImmobileInfoDTO> newImmobile = Optional.of(new GetImmobileInfoDTO());
        RispostaDTO rispostaDTO = new RispostaDTO();
        Optional<Domanda> domanda = Optional.of(new Domanda());


        when(authService.getAuthUtente()).thenReturn(authUser);

        when(rispostaService.addNewRisposta(rispostaDTO, 1L, authUser.get(),1L))
                .thenReturn(risposta);

        when(immobileService.getImmobileById(1l)).thenReturn(newImmobile);

        String viewName = domandaController.addRispostaToDomanda(rispostaDTO,1L, 1L, model);

        assertEquals("redirect:/site/immobile/mioImmobile/1", viewName);
        assertTrue(model.containsAttribute("message"));
    }

    @Test
    void addRispostaToDomanda_unautenticated_unsuccess() throws UtenteException, ImmobileException, DomandaException {
        Optional<Risposta> risposta = Optional.of(new Risposta());
        Optional<GetImmobileInfoDTO> newImmobile = Optional.of(new GetImmobileInfoDTO());
        RispostaDTO rispostaDTO = new RispostaDTO();
        Domanda newDomanda = new Domanda();

        when(authService.getAuthUtente()).thenThrow(UtenteException.class);

        when(rispostaService.addNewRisposta(rispostaDTO, 1L, authUser.get(),1L))
                .thenReturn(risposta);


        String viewName = domandaController.addRispostaToDomanda(rispostaDTO,1L, 1L, model);

        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));
    }

    @Test
    void addRispostaToDomanda_autenticated_risposta_not_found()
            throws UtenteException, ImmobileException, RispostaException, DomandaException {
        Optional<Risposta> risposta = Optional.of(new Risposta());
        Optional<GetImmobileInfoDTO> newImmobile = Optional.of(new GetImmobileInfoDTO());
        RispostaDTO rispostaDTO = new RispostaDTO();
        Optional<Domanda> domanda = Optional.of(new Domanda());

        when(authService.getAuthUtente()).thenReturn(authUser);

        when(rispostaService.addNewRisposta(rispostaDTO, 1L, authUser.get(),1L))
                .thenReturn(Optional.empty());



        String viewName = domandaController.addRispostaToDomanda(rispostaDTO,1L, 1L, model);

        assertEquals("Fail", viewName);
        assertTrue(model.containsAttribute("error"));
    }

}