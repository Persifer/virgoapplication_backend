package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.InsertOffertaDTO;
import com.application.virgo.exception.*;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Immobile;
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
                .idUtente(1L)
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
        assertEquals("Offerte", viewName);
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
        when(offertaUtenteService.saveOffertaToUtente(authenticatedUser, newOfferta, idProprietario))
                .thenReturn(Optional.of(newOffertaToUtente));

        String viewName = offertaController.createProposta(idProprietario, idImmobile, tempOffertaDTO, model);

        verify(offertaUtenteService).saveOffertaToUtente(authenticatedUser, newOfferta, idProprietario);
        assertEquals("redirect:/site/immobile/viewImmobile/2", viewName);
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
        InsertOffertaDTO insertOffertaDTO =
                InsertOffertaDTO.builder()
                        .idProprietario(1L)
                        .idImmobile(1L)
                        .build();
        Offerta offerta = Offerta.builder()
                .idOfferta(1l)
                .build();

        OfferteUtente newOffertaUtente = OfferteUtente.builder()
                .offertaInteressata(Offerta.builder().idImmobileInteressato(
                                Immobile.builder()
                                        .idImmobile(1L)
                                        .build()
                        )
                        .build())
                .offerente(Utente.builder()
                        .idUtente(1L)
                        .build())
                .proprietario(Utente.builder()
                        .idUtente(1L)
                        .build())
                .build();

        when(authService.getAuthUtente()).thenReturn(Optional.of(authenticatedUser));
        when(offertaService.createNewOfferta(insertOffertaDTO)).thenReturn(
                Optional.of(offerta)
        );
        when(offertaUtenteService.rilanciaOffertaToUtente(
                authenticatedUser, offerta, 1L, Boolean.TRUE)).thenReturn(Optional.of(newOffertaUtente));

        String result = offertaController.rilanciaOfferta(Boolean.TRUE, insertOffertaDTO ,model);

        assertEquals("redirect:/site/utente/getListaOfferte/storico/1/1", result);

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
        assertEquals("redirect:/site/utente/getInfo", viewName);
        assertTrue(model.containsKey("okmessage"));
    }

    @Test
    void acceptOfferta_authenticatedUserNotPresent_returnsLoginView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException, ContrattoException, ContrattoUtenteException {
        Long idOfferta = 1L;

        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        String viewName = offertaController.acceptOfferta(idOfferta, 1l,1, model);

        assertEquals("Login", viewName);
        assertTrue(model.containsKey("error"));
    }

    @Test
    void declineOfferta_validInput_declinesOffertaAndRedirectsToOffertaView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException{
        Long idOfferta = 1L;

        OfferteUtente declinedOfferta = OfferteUtente.builder()
                .offertaInteressata(Offerta.builder().idImmobileInteressato(
                                Immobile.builder()
                                        .idImmobile(1L)
                                        .build()
                        )
                        .build())
                .offerente(Utente.builder()
                        .idUtente(1L)
                        .build())
                .proprietario(Utente.builder()
                        .idUtente(1L)
                        .build())
                .build();

        //OfferteUtente declinedOfferta = new OfferteUtente();

        when(authService.getAuthUtente()).thenReturn(Optional.of(authenticatedUser));
        when(offertaUtenteService.declineOfferta(idOfferta, authenticatedUser)).thenReturn(Optional.of(declinedOfferta));

        String viewName = offertaController.declineOfferta(idOfferta,1, model);

        assertEquals("redirect:/site/utente/getListaOfferte/storico/1/1", viewName);
    }

    @Test
    void declineOfferta_validInput_declinesOffertaAndRedirectsToPropostaView() throws UtenteException, OffertaException,
            ImmobileException, OffertaUtenteException{
        Long idOfferta = 1L;
        OfferteUtente declinedOfferta = OfferteUtente.builder()
                .offertaInteressata(Offerta.builder().idImmobileInteressato(
                                Immobile.builder()
                                        .idImmobile(1L)
                                        .build()
                        )
                        .build())
                .offerente(Utente.builder()
                        .idUtente(1L)
                        .build())
                .proprietario(Utente.builder()
                        .idUtente(1L)
                        .build())
                .build();

        when(authService.getAuthUtente()).thenReturn(Optional.of(authenticatedUser));
        when(offertaUtenteService.declineOfferta(idOfferta, authenticatedUser)).thenReturn(Optional.of(declinedOfferta));

        String viewName = offertaController.declineOfferta(idOfferta,0, model);

        verify(offertaUtenteService).declineOfferta(idOfferta, authenticatedUser);
        assertEquals("redirect:/site/utente/getListProposte/storico/1/1", viewName);
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