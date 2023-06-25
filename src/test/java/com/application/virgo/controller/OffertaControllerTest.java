package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.exception.*;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.OffertaService;
import com.application.virgo.service.interfaces.OffertaUtenteService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class OffertaControllerTest {

    @Mock
    private OffertaService offertaService;

    @Mock
    private OffertaUtenteService offertaUtenteService;

    @Mock
    private AuthService authService;

    private OffertaController offertaController;

    private Utente authenticatedUser;
    private Offerta newOfferta;
    private ModelMap model;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        offertaController = new OffertaController(offertaService, offertaUtenteService, authService);
        authenticatedUser = Utente.builder()
                .email("mail@mail.it")
                .password("$2a$10$qwkYqMJwaQFb/B/V4YKq9Os/JUBUqZkwUdum5Ul2oEOP86pPHEFFm")
                .nome("Antonio")
                .cognome("Giorgino")
                .build();

        newOfferta = new Offerta();
        model = new ModelMap();
    }

    @Test
    void get_returnsOffertaView() {
        String viewName = offertaController.get(model);
        assertEquals("Offerta", viewName);
    }

    @SneakyThrows
    @Test
    void createProposta_validInput_createsOffertaAndRedirectsToOffertaView() {
        InsertOffertaDTO tempOffertaDTO = new InsertOffertaDTO();
        Long idProprietario = 1L;
        Long idImmobile = 2L;


        OfferteUtente newOffertaToUtente = new OfferteUtente();

        when(authService.getAuthUtente()).thenReturn(Optional.of(authenticatedUser));
        when(offertaService.createNewOfferta(tempOffertaDTO)).thenReturn(Optional.of(newOfferta));
        when(offertaUtenteService.saveOffertaToUtente(authenticatedUser, newOfferta, idProprietario)).thenReturn(Optional.of(newOffertaToUtente));

        String viewName = offertaController.createProposta(idProprietario, idImmobile, tempOffertaDTO, model);

        verify(offertaUtenteService).saveOffertaToUtente(authenticatedUser, newOfferta, idProprietario);
        assertEquals("Offerta", viewName);
        assertTrue(model.containsKey("message"));
    }

    @Test
    void createProposta_invalidInput_returnsFailView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException {
        InsertOffertaDTO tempOffertaDTO = new InsertOffertaDTO();
        Long idProprietario = 1L;
        Long idImmobile = 2L;

        when(authService.getAuthUtente()).thenReturn(Optional.of(authenticatedUser));
        when(offertaService.createNewOfferta(tempOffertaDTO)).thenReturn(Optional.empty());

        String viewName = offertaController.createProposta(idProprietario, idImmobile, tempOffertaDTO, model);

        assertEquals("Fail", viewName);
        assertTrue(model.containsKey("error"));
        verify(offertaUtenteService, never()).saveOffertaToUtente(any(), any(), any());
    }

    @Test
    void createProposta_authenticatedUserNotPresent_returnsLoginView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException {
        InsertOffertaDTO tempOffertaDTO = new InsertOffertaDTO();
        Long idProprietario = 1L;
        Long idImmobile = 2L;

        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        String viewName = offertaController.createProposta(idProprietario, idImmobile, tempOffertaDTO, model);

        assertEquals("Login", viewName);
        assertTrue(model.containsKey("error"));
        verify(offertaService, never()).createNewOfferta(any());
        verify(offertaUtenteService, never()).saveOffertaToUtente(any(), any(), any());
    }

    @Test
    void rilanciaOfferta_returnsEmptyString() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException{
        Long idOfferta = 1L;

        String result = offertaController.rilanciaOfferta(1, new InsertOffertaDTO() ,model);

        assertEquals("", result);
        assertEquals(0, model.size());
    }

    @Test
    void acceptOfferta_validInput_acceptsOffertaAndRedirectsToOffertaView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException, ContrattoException, ContrattoUtenteException {
        Long idOfferta = 1L;

        ContrattoUtente acceptedOfferta = new ContrattoUtente();

        when(authService.getAuthUtente()).thenReturn(Optional.of(authenticatedUser));
        when(offertaUtenteService.acceptOfferta(idOfferta, authenticatedUser, 1L)).thenReturn(Optional.of(acceptedOfferta));

        String viewName = offertaController.acceptOfferta(idOfferta, 1l,1,model);

        verify(offertaUtenteService).acceptOfferta(idOfferta, authenticatedUser,1l);
        assertEquals("Offerta", viewName);
        assertTrue(model.containsKey("error"));
    }

    @Test
    void acceptOfferta_authenticatedUserNotPresent_returnsLoginView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException, ContrattoException, ContrattoUtenteException {
        Long idOfferta = 1L;

        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        String viewName = offertaController.acceptOfferta(idOfferta, 1l,1, model);

        assertEquals("Login", viewName);
        assertTrue(model.containsKey("error"));
        verify(offertaUtenteService, never()).acceptOfferta(anyLong(), any(),1l);
    }

    @Test
    void declineOfferta_validInput_declinesOffertaAndRedirectsToOffertaView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException{
        Long idOfferta = 1L;

        OfferteUtente declinedOfferta = new OfferteUtente();

        when(authService.getAuthUtente()).thenReturn(Optional.of(authenticatedUser));
        when(offertaUtenteService.declineOfferta(idOfferta, authenticatedUser)).thenReturn(Optional.of(declinedOfferta));

        String viewName = offertaController.declineOfferta(idOfferta,1, model);

        verify(offertaUtenteService).declineOfferta(idOfferta, authenticatedUser);
        assertEquals("offerta", viewName);
        assertTrue(model.containsKey("error"));
    }

    @Test
    void declineOfferta_authenticatedUserNotPresent_returnsLoginView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException{
        Long idOfferta = 1L;

        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        String viewName = offertaController.declineOfferta(idOfferta, 1,model);

        assertEquals("login", viewName);
        assertTrue(model.containsKey("error"));
        verify(offertaUtenteService, never()).declineOfferta(anyLong(), any());
    }
}