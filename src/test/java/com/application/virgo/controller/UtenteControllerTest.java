package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.*;
import com.application.virgo.exception.ContrattoUtenteException;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.service.interfaces.UtenteService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UtenteControllerTest {

    @Mock
    private UtenteService utenteService;

    @Mock
    private AuthService authService;

    @Mock
    private ContrattoUtenteService contrattoUtenteService;

    @InjectMocks
    private UtenteController utenteController;

    private ModelMap model;
    private Optional<Utente> authUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        utenteController = new UtenteController(utenteService, authService, contrattoUtenteService);
        model = new ModelMap();

        authUser = Optional.of(
                Utente.builder()
                        .idUtente(1L)
                        .nome("antonio")
                        .cognome("giorgino")
                        .email("mail@mail.it")
                        .password("$2a$10$qwkYqMJwaQFb/B/V4YKq9Os/JUBUqZkwUdum5Ul2oEOP86pPHEFFm")
                        .build());
    }

    @Test
    void get_returnsUtenteView() {
        String viewName = utenteController.get();
        assertEquals("Utente", viewName);
    }

   /* @Test
    void updateUtenteInformation_validInput_UpdateUtenteAndReturnToUtentePage() throws UtenteException {
        Long idUtenteDaModificare = 1L;
        UtenteDTO updatedUtente = new UtenteDTO();

        when(authService.getAuthUtente()).thenReturn(authUser);

        String viewName = utenteController.updateUtenteInformation(idUtenteDaModificare, updatedUtente, model);

        verify(utenteService).updateUtenteInfoById(idUtenteDaModificare, updatedUtente);
        assertEquals("Utente", viewName);
        assertTrue(model.containsAttribute("message"));
    }

    @Test
    void updateUtenteInformation_unauthenticatedUser_redirectErroneousToLogin() throws UtenteException {
        Long idUtenteDaModificare = 1L;
        UtenteDTO updatedUtente = new UtenteDTO();

        when(authService.getAuthUtente()).thenReturn(authUser);

        String viewName = utenteController.updateUtenteInformation(idUtenteDaModificare, updatedUtente, model);

        verifyNoInteractions(utenteService);
        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));
    }

    @Test
    void updateUtenteInformation_UtenteException_occurs_redirectErronoeusToFail() throws UtenteException {
        Long idUtenteDaModificare = 1L;
        UtenteDTO updatedUtente = new UtenteDTO();

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(utenteService.updateUtenteInfoById(idUtenteDaModificare, updatedUtente)).thenThrow(new UtenteException("Error message"));

        String viewName = utenteController.updateUtenteInformation(idUtenteDaModificare, updatedUtente, model);

        assertEquals("Fail", viewName);
        assertTrue(model.containsAttribute("error"));
    }

    @Test
    void getUsernameInformation_authenticatedUser_redirectSuccessfulToLogin() throws UtenteException {

        Optional<Utente> utenteInfo = Optional.of(new Utente());
        when(authService.getAuthUtente()).thenReturn(utenteInfo);

        String viewName = utenteController.getUsernameInformation(model);

        assertEquals("Utente", viewName);
        assertTrue(model.containsAttribute("utente"));
    }

    @Test
    void getUsernameInformation_unauthenticatedUser_redirectErroneousToFail() throws UtenteException {

        Optional<Utente> utenteInfo = Optional.empty();
        when(authService.getAuthUtente()).thenReturn(utenteInfo);

        String viewName = utenteController.getUsernameInformation(model);

        assertEquals("Fail", viewName);
        assertTrue(model.containsAttribute("error"));
    }*/

    @Test
    void getOfferteRicevute_authenticatedUser_redirectSuccessfulToPage() throws OffertaUtenteException, UtenteException {
        ModelMap model = new ModelMap();
        Long offset = 0L;
        Long pageSize = 10L;

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(utenteService.getListaProposte(authUser.get()))
                .thenReturn(Instancio.ofList(ViewListaOfferteDTO.class).size(10).create());

        String viewName = utenteController.getOfferteRicevute(model);

        assertEquals("Ciao", viewName);
        assertTrue(model.containsAttribute("listaOfferte"));
    }

    @Test
    void getOfferteRicevute_unauthenticatedUser_redirectSuccessfulToLogin() throws UtenteException {
        Long offset = 0L;
        Long pageSize = 10L;

        Optional<Utente> authUser = Optional.empty();
        when(authService.getAuthUtente()).thenReturn(authUser);

        String viewName = utenteController.getOfferteRicevute(model);

        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));
    }

    @Test
    void getOfferteRicevute_UtenteExceptionOrOffertaUtenteException_occurs_redirectErroneousToLogin()
            throws UtenteException, OffertaUtenteException {
        Long offset = 0L;
        Long pageSize = 10L;

        when(authService.getAuthUtente()).thenReturn(authUser);

        when(utenteService.getListaProposte(authUser.get())).thenThrow(
                new OffertaUtenteException("Impossibile reperire l'offerta"));

        String viewName = utenteController.getOfferteRicevute(model);

        assertEquals("Utente", viewName);
        assertTrue(model.containsAttribute("error"));
    }


    /**
     * Test che controlla il corretto funzionamento, con utente autenticato, del metodo getOfferteBetweenUtenti
     */
    @Test
    void getOfferteBetweenUtenti_authenticated_redirectOkToOffertePage()
            throws UtenteException, ImmobileException {
        Long idOfferente = 1L;
        Long idImmobile = 1L;

        when(authService.getAuthUtente()).thenReturn(authUser);

        when(utenteService.getAllProposteBetweenUtenti(authUser.get(),
                idOfferente, idImmobile)).thenReturn(Instancio.ofList(ViewOfferteBetweenUtentiDTO.class).size(10).create());

        String viewName = utenteController.getOfferteBetweenUtenti(model, idOfferente, idImmobile);

        assertEquals("Offerte", viewName);
        assertNotEquals(Collections.emptyList(), model.getAttribute("listaOfferte"));


    }


    /**
     * Test per controllare se, nel caso in cui l'utente non sia autenticato, restituisca la pagina di Login
     */
    @Test
    void getOfferteBetweenUtenti_authenticated_redirectErroneousToLogin()
            throws UtenteException, ImmobileException {
        Long idOfferente = 1L;
        Long idImmobile = 1L;

        when(authService.getAuthUtente()).thenReturn(Optional.empty());
        when(utenteService.getAllProposteBetweenUtenti(authUser.get(),
                idOfferente, idImmobile)).thenThrow(UtenteException.class);

        String viewName = utenteController.getOfferteBetweenUtenti(model, idOfferente, idImmobile);

        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));
    }


    /**
     * Test per controllare il corretto funzionamento del metodo getOfferte quando tutti i dati passati sono passasti correttamente
     */
    @Test
    void getOfferte_autenticated_redirecSuccessfullToCiao() throws UtenteException, OffertaUtenteException {
        Long offset = 0L;
        Long pageSize = 10L;

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(utenteService.getListaOfferte(Mockito.any(Utente.class)))
                .thenReturn(Instancio.ofList(ViewListaOfferteDTO.class).size(10).create());

        String viewName = utenteController.getOfferte(model);

        assertEquals("Ciao", viewName);
        assertNotEquals(Collections.emptyList(), model.getAttribute("listaOfferte"));

    }


    /**
     * Test per controllare il funzionamento del metodo getOfferte quando non esiste un utente autenticato
     */
    @Test
    void getOfferte_unautenticated_redirectErroneousToLogin() throws UtenteException, OffertaUtenteException {
        Long offset = 0L;
        Long pageSize = 10L;

        when(authService.getAuthUtente()).thenThrow(UtenteException.class);

        String viewName = utenteController.getOfferte(model);

        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));

    }


    /**
     * Test per controllare il funzionamento del metodo getOfferte quando viene lanciata un'eccezione dal metodo del service
     * che richiamo per eseguire la business logic
     */
    @Test
    void getOfferte_unautenticated_inService_redirectErroneousToLogin() throws UtenteException, OffertaUtenteException {

        when(authService.getAuthUtente()).thenThrow(UtenteException.class);
        when(utenteService.getListaOfferte(Mockito.any(Utente.class)))
                .thenThrow(UtenteException.class);

        String viewName = utenteController.getOfferteBetweenUtenti(model,0L, 20L);

        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));

    }


    /**
     * Metodo per testare il corretto funzionamento del metodo
     */
    @Test
    void getStoricoOfferte_autenticated_redirectSuccessfullToUtente()
            throws UtenteException, OffertaUtenteException, ImmobileException {
        Long offset = 1L;
        Long pageSize = 1L;

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(utenteService.getAllOfferteBetweenUtenti(Mockito.any(Utente.class), Mockito.any(Long.class), Mockito.any(Long.class)))
                .thenReturn(Instancio.ofList(ViewOfferteBetweenUtentiDTO.class).size(10).create());

        String viewName = utenteController.getStoricoOfferte(model, offset, pageSize);

        assertEquals("Ciao", viewName);
        assertTrue(model.containsAttribute("listaOfferte"));
    }

    /**
     * Controlla il comportamento di getStoricoOfferte nel caso di utente non autenticato
     * @throws UtenteException se l'utente non è autenticato
     * @throws ImmobileException se l'immobile non esiste
     */
    @Test
    void getStoricoOfferte_unautenticated_redirectErroneousToLogin()
            throws UtenteException, ImmobileException {

        Long idUtente = 1L;
        Long idImmobile = 1L;

        when(authService.getAuthUtente()).thenThrow(UtenteException.class);

        String viewName = utenteController.getOfferteBetweenUtenti(model, idUtente, idImmobile);

        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));
    }

    /**
     * Testa in caso di errore all'interno della business logic del componente
     * @throws UtenteException se l'utente non è autenticato
     * @throws ImmobileException se l'immobile non esiste
     */
    @Test
    void getStoricoOfferte_unautenticated_redirectErroneousToUtente()
            throws UtenteException, ImmobileException {

        Long idUtente = 1L;
        Long idImmobile = 1L;

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(utenteService.getAllOfferteBetweenUtenti(authUser.get(),idUtente, idImmobile))
                .thenThrow(ImmobileException.class);

        String viewName = utenteController.getStoricoOfferte(model, idUtente, idImmobile);


        assertEquals("Utente", viewName);
        assertTrue(model.containsAttribute("error"));
    }

    /**
     * Test del comportamento di getListaContratti nel caso in cui tutto sia corretto
     * @throws UtenteException se l'utente non è autenticato
     * @throws ContrattoUtenteException se i contratti non esistono
     */
    @Test
    void getListaContratti_autenticated_redirectSuccessfullToUtente()
            throws UtenteException, ContrattoUtenteException {

        Long offset = 1L;
        Long pageSize = 1L;

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(contrattoUtenteService.getListaContrattiForUtente(Mockito.any(Utente.class), Mockito.any(Long.class), Mockito.any(Long.class)))
                .thenReturn(Instancio.ofList(ContrattiUtenteDTO.class).size(10).create());

        String viewName = utenteController.getListaContratti(model, offset, pageSize);

        assertEquals("Ciao", viewName);
        assertTrue(model.containsAttribute("listaContratti"));

    }

    @Test
    void getListaContratti_unautenticated_redirectErroneousToLogin()
            throws UtenteException, ContrattoUtenteException {

        when(authService.getAuthUtente()).thenThrow(UtenteException.class);

        String viewName = utenteController.getListaContratti(model, 0L,20L);

        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));

    }


    /**
     * Test del comportamento di getListaContratti nel caso in cui tutto sia corretto
     * @throws UtenteException se l'utente non è autenticato
     * @throws ContrattoUtenteException se i contratti non esistono
     */
    @Test
    void getSingleContratto_autenticated_redirectSuccessfullToUtente()
            throws UtenteException, ContrattoUtenteException {

        when(authService.getAuthUtente()).thenReturn(authUser);
        /*when(contrattoUtenteService.getDettagliContratto(authUser.get(), 1L))
                .thenReturn(Optional.of(new DettagliContrattoDTO()));*/

        String viewName = utenteController.getSingleContratto(model, 1L);

        assertEquals("Ciao", viewName);
       assertTrue(model.containsAttribute("listaContratti"));

    }

    @Test
    void getSingleContratto_unautenticated_redirectErroneousToLogin()
            throws UtenteException, ContrattoUtenteException {

        when(authService.getAuthUtente()).thenThrow(UtenteException.class);

        String viewName = utenteController.getSingleContratto(model, 1L);

        assertEquals("Login", viewName);
        assertTrue(model.containsAttribute("error"));

    }

}