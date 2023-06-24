package com.application.virgo.controller;


import com.application.virgo.DTO.inputDTO.DomandaDTO;
import com.application.virgo.DTO.outputDTO.GetImmobileInfoDTO;
import com.application.virgo.DTO.outputDTO.GetUtenteImmobiliDTO;
import com.application.virgo.DTO.outputDTO.HomeImmobileDTO;
import com.application.virgo.DTO.outputDTO.ViewListaOfferteDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.ImmobileService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.application.virgo.DTO.inputDTO.ImmobileDTO;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImmobileControllerTest {


    @Mock
    private ImmobileService immobileService;
    @Mock
    private AuthService authService;

    @InjectMocks
    private ImmobileController immobileController;

    private ModelMap model;
    private Optional<Utente> authUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    void test_returnCreaImmobilePage(){
        String viewName = immobileController.returnCreaImmobilePage(model);
        assertEquals("CreaImmobile", viewName);
    }

    @Test
    void testCreateNewImmobile_Success() throws UtenteException, ImmobileException {
        ImmobileDTO immobileDTO = new ImmobileDTO();

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(immobileService.createNewImmobile(immobileDTO, authUser.get()))
                .thenReturn(Optional.of(immobileDTO));

        String viewName = immobileController.createNewImmobile(immobileDTO, model);
        assertEquals("Home", viewName);
        assertEquals("Immobile creato con successo", model.get("message"));
    }

    @Test
    void testCreateNewImmobile_NotAuthenticated() throws UtenteException {
        ImmobileDTO immobileDTO = new ImmobileDTO();
        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        String viewName = immobileController.createNewImmobile(immobileDTO, model);
        assertEquals("Fail2", viewName);
        assertEquals("Errore", model.get("error"));
    }

    @Test
    void testCreateNewImmobile_Error() throws UtenteException, ImmobileException {
        ImmobileDTO immobileDTO = new ImmobileDTO();

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(immobileService.createNewImmobile(immobileDTO, authUser.get()))
                .thenReturn(Optional.empty());

        String viewName = immobileController.createNewImmobile(immobileDTO, model);
        assertEquals("Fail1", viewName);
        assertEquals("Errore nella creazione di un immobile, riprovare", model.get("error"));
    }



    @Test
    void testGetImmobileInformation() throws ImmobileException, UtenteException {
        // Setup
        GetImmobileInfoDTO storedImmobile = new GetImmobileInfoDTO();

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(immobileService.getImmobileById(1L)).thenReturn(Optional.of(storedImmobile));

        // Execute
        String viewName = immobileController.getImmobileInformation(1L, model);

        // Verify
        assertEquals("Immobile", viewName);
        assertEquals(storedImmobile, model.get("wantedImmobile"));
        assertEquals(new DomandaDTO(), model.get("tempNewDomandaDTO"));
    }

    @Test
    void testGetImmobileInformation_ThrowsException() throws ImmobileException, UtenteException {
        // Setup

        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        // Execute
        String viewName = immobileController.getImmobileInformation(1L, model);

        // Verify
        assertEquals("Login", viewName);
        assertEquals("Bisogna essere autenticato per richiedere un immobile", model.get("error"));
    }

    @Test
    void testGetImmobileInformation_ImmobileNotFound() throws ImmobileException, UtenteException {
        // Setup


        when(authService.getAuthUtente()).thenReturn(authUser);
        when(immobileService.getImmobileById(1L)).thenReturn(Optional.empty());

        // Execute
        String viewName = immobileController.getImmobileInformation(1L, model);

        // Verify
        assertEquals("Fail", viewName);
        assertEquals("L'immobile voluto non è presente", model.get("error"));
    }

    @Test
    void testGetImmobileInformationForUpdate() throws ImmobileException, UtenteException {
        // Setup
        GetUtenteImmobiliDTO storedImmobile = new GetUtenteImmobiliDTO();

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(immobileService.getImmobileByIdToUpdate(1L, authUser.get())).thenReturn(Optional.of(storedImmobile));

        // Execute
        String viewName = immobileController.getImmobileInformationForUpdate(1L, model);

        // Verify
        assertEquals("Immobile", viewName);
        assertEquals("Informazioni aggiornate con successo", model.get("message"));
    }

    @Test
    void testGetImmobileInformationForUpdate_ThrowsException() throws ImmobileException, UtenteException {

        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        // Execute
        String viewName = immobileController.getImmobileInformationForUpdate(1L, model);

        // Verify
        assertEquals("Login", viewName);
        assertEquals("Bisogna esssere autenticati per aggiornare le informazioni", model.get("error"));
    }

// =====================================================================================================================

    @Test
    void testGetListaImmobiliUtente() throws ImmobileException, UtenteException {
        // Setup

        List<GetUtenteImmobiliDTO> foundedImmobili = List.of(new GetUtenteImmobiliDTO());

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(immobileService.getUtenteListaImmobili(0L, 20L, authUser.get()))
                .thenReturn(foundedImmobili);

        // Execute
        String viewName = immobileController.getListaImmobiliUtente(0L, 20L, model);

        // Verify
        assertEquals("Utente", viewName);
        assertEquals(foundedImmobili, model.get("listaImmobili"));
    }

    @Test
    void testGetListaImmobiliUtente_ThrowsException() throws ImmobileException, UtenteException {
        // Setup

        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        // Execute
        String viewName = immobileController.getListaImmobiliUtente(0L, 20L, model);

        // Verify
        assertEquals("Login", viewName);
        assertEquals("Bisogna essere autenticati per prendere questa informazione", model.get("listaImmobili"));
    }

    @Test
    void testGetListImmobili() throws ImmobileException, UtenteException {
        // Setup

        List<HomeImmobileDTO> foundedImmobili = List.of(new HomeImmobileDTO());

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(immobileService.getAllImmobiliPaginated(0L, 20L)).thenReturn(foundedImmobili);

        // Execute
        String viewName = immobileController.getListImmobili(0L, 20L, model);

        // Verify
        assertEquals("Home", viewName);
        assertEquals(foundedImmobili, model.get("listImmobili"));
    }

    @Test
    void testGetListImmobili_ThrowsException() throws ImmobileException, UtenteException {
        // Setup
        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        // Execute
        String viewName = immobileController.getListImmobili(0L, 20L, model);

        // Verify
        assertEquals("Fail", viewName);
        assertEquals("Dentro else 1", model.get("error"));
    }

    @Test
    void testModifyImmobileInfo() throws UtenteException, ImmobileException {
        // Setup
        ImmobileDTO tempUpdatedimmobileDTO = new ImmobileDTO();
        Long idImmobile = 1L;
        Optional<Immobile> newImmobile = Optional.of(new Immobile());

        when(authService.getAuthUtente()).thenReturn(authUser);
        when(immobileService.updateImmobileInformation(tempUpdatedimmobileDTO, authUser.get(), idImmobile))
                .thenReturn(newImmobile);

        // Execute
        String viewName = immobileController.modifyImmobileInfo(tempUpdatedimmobileDTO, idImmobile, model);

        // Verify
        assertEquals("Immobile", viewName);
        assertEquals("Domanda inserita con successo", model.get("message"));
    }

    @Test
    void testModifyImmobileInfo_ThrowsException() throws UtenteException, ImmobileException {
        // Setup
        ImmobileDTO tempUpdatedimmobileDTO = new ImmobileDTO();
        Long idImmobile = 1L;

        when(authService.getAuthUtente()).thenReturn(Optional.empty());

        // Execute
        String viewName = immobileController.modifyImmobileInfo(tempUpdatedimmobileDTO, idImmobile, model);

        // Verify
        assertEquals("Fail2", viewName);
        assertEquals("Bisogna essere autenticati per prendere questa informazione", model.get("error"));
    }



}