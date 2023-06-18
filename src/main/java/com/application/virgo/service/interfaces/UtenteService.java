package com.application.virgo.service.interfaces;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.ViewListaOfferteDTO;
import com.application.virgo.DTO.outputDTO.ViewOfferteBetweenUtentiDTO;
import com.application.virgo.exception.ImmobileException;
import com.application.virgo.exception.OffertaUtenteException;
import com.application.virgo.exception.UtenteException;
import com.application.virgo.model.Domanda;
import com.application.virgo.model.Utente;
import jakarta.mail.MessagingException;

import java.util.List;
import java.util.Optional;

public interface UtenteService {

    Optional<Utente> getUtenteByEmailAndPassword(String username, String password);

    Optional<UtenteDTO> getUtenteById(Long idUtenteToFound) throws UtenteException;

    Optional<Utente> getUtenteClassByEmail(String idUtenteToFound) throws UtenteException;

    Optional<Utente> updateUtenteInfoById(Long idUtente, UtenteDTO newUtente) throws UtenteException;

    Optional<Utente> tryRegistrationHandler(UtenteDTO newUtente) throws UtenteException, MessagingException;

    void addDomandaToUtente(Utente authUser, Domanda domandaToAdd) throws UtenteException;

    Optional<Utente> getUtenteClassById(Long idProprietario) throws UtenteException;

    List<ViewListaOfferteDTO> getListaProposte(Utente propietario, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException;

    List<ViewListaOfferteDTO> getListaOfferte(Utente propietario, Long offset, Long pageSize)
            throws OffertaUtenteException, UtenteException;

    List<ViewOfferteBetweenUtentiDTO> getAllProposteBetweenUtenti(Utente proprietario, Long idOfferente, Long idImmobile)
            throws UtenteException, ImmobileException;

    List<ViewOfferteBetweenUtentiDTO> getAllOfferteBetweenUtenti(Utente proprietario, Long idOfferente, Long idImmobile)
            throws UtenteException, ImmobileException;
}
