package com.application.virgo.controller;

import com.application.virgo.DTO.inputDTO.UtenteDTO;
import com.application.virgo.DTO.outputDTO.*;
import com.application.virgo.exception.*;
import com.application.virgo.model.Utente;
import com.application.virgo.service.interfaces.AuthService;
import com.application.virgo.service.interfaces.ContrattoUtenteService;
import com.application.virgo.service.interfaces.ImmobileService;
import com.application.virgo.service.interfaces.UtenteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
* Classe controller che si occupa di gestire tutte le richieste legate ad un utente
* Al suo interno ci sono tutti i metodi che servono per gestire l'aggiornamento di utente, la visualizzazione delle caht, contratti ed altro
* */
@Controller
@RequestMapping("/site/utente")
@AllArgsConstructor
@Validated
public class UtenteController {


    private UtenteService utenteService;
    private ImmobileService immobileService;
    private AuthService authService;
    private ContrattoUtenteService contrattoUtenteService;

    /**
     * Permette di avere la home page dell'utente
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @param offset indice iniziale
     * @param pageSize numero elementi
     * @return la view della home utente
     */
    @GetMapping("/{offset}/{pageSize}")
    public String get(ModelMap model,@PathVariable("offset") Long offset,
                      @PathVariable("pageSize") Long pageSize) {
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                // se l'utente p autenticato preleva la sua lista di immobili
                List<GetUtenteImmobiliDTO> listImmobili = immobileService.getUtenteListaImmobili(offset, pageSize, authUser.get());
                if(!listImmobili.isEmpty()){
                    // ritorna la view utente con la lista
                    model.addAttribute("listaImmobiliUtente", listImmobili);
                    return "Utente";
                }else{
                    model.addAttribute("listaImmobiliUtente", List.of());
                    return "Utente";
                }

            }else{
                model.addAttribute("message", "Bisogna essere autenticati per poter aggiornare l'utente");
                return "Login";
            }

        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (ImmobileException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }

    }


    /**
     * Pagine che serve per reperire le informazioni di un utente per l'aggiornamento
     * @param model serve per passare i dati al front-end
     * @return le informazioni di un utente
     */
    @GetMapping("/getInfo")
    public String getUsernameInformation(ModelMap model ){
        try{
            Optional<Utente> utenteInfo = authService.getAuthUtente();
            if(utenteInfo.isPresent()){
                model.addAttribute("utente", utenteInfo.get());
                return "InfoUtente";
            }else{
                model.addAttribute("error", "Utente non trovato");
                return "Fail";
            }

        }catch (UtenteException error){
            model.addAttribute("error", "Utente non trovato");
            return "Fail";
        }
    }

    /**
     * Permette di prelevare la lista delle proposte ricevute di un utente
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @return la view delle liste delle proposte
     */
    @GetMapping("/getListProposte")
    public String getOfferteRicevute(ModelMap model){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                // se l'utente è autenticato ritorno la lista degli utenti con cui ha una chat
                List<ViewListaOfferteDTO> listaProposte = utenteService.getListaProposte(authUser.get());
                model.addAttribute("listaProposte", listaProposte);
                model.addAttribute("isAcquirente", "0");
                return "Proposte";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "Login";
            }
        }catch (UtenteException | OffertaUtenteException | ImmobileException error){
            model.addAttribute("error", "Utente non trovato");
            return "Utente";
        }
    }

    /**
     * Permette di avere la lista degli scambi tra due utenti
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @param idOfferente id utente controparte
     * @param idImmobile id immobile interessante
     * @return la view della chat
     */
    @GetMapping("/getListProposte/storico/{idOfferente}/{idImmobile}")
    public String getOfferteBetweenUtenti(ModelMap model, @PathVariable("idOfferente") Long idOfferente, @PathVariable("idImmobile") Long idImmobile){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                // ritorna la lsita degli scambi tra due utenti
                List<ViewOfferteBetweenUtentiDTO> listaOfferte =
                        utenteService.getAllProposteBetweenUtenti(authUser.get(), idOfferente, idImmobile);
                model.addAttribute("listaOfferte", listaOfferte);
                model.addAttribute("isAcquirente", "0");
                return "SingolaProposta";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "Login";
            }
        }catch (UtenteException | ImmobileException error){
            model.addAttribute("error", "Utente non trovato");
            return "Login";
        }
    }

    /**
     * Permette di prelevare la lista delle offerte inviate di un utente
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @return la view delle liste delle offerte con altri utenti
     */
    @GetMapping("/getListaOfferte")
    public String getOfferte(ModelMap model){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                // ritorna la lsita delle chat con altri utenti
                List<ViewListaOfferteDTO> listaOfferte = utenteService.getListaOfferte(authUser.get());
                model.addAttribute("listaOfferte", listaOfferte);
                return "Offerte";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "Login";
            }
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (OffertaUtenteException | ImmobileException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }
    }

    /**
     * Permette di avere la lista degli scambi tra due utenti
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @param idUtente id utente controparte
     * @param idImmobile id immobile interessante
     * @return la view della chat
     */
    @GetMapping("/getListaOfferte/storico/{id_utente}/{id_immobile}")
    public String getStoricoOfferte(ModelMap model,
                                    @PathVariable("id_utente") Long idUtente,
                                    @PathVariable("id_immobile") Long idImmobile){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ViewOfferteBetweenUtentiDTO> listaOfferte =
                        utenteService.getAllOfferteBetweenUtenti(authUser.get(), idUtente, idImmobile);
                model.addAttribute("listaOfferte", listaOfferte);
                model.addAttribute("isAcquirente", "1");
                return "SingolaOfferta";
            }else{
                model.addAttribute("error", "Utente non autenticato");
                return "Login";
            }
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (ImmobileException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }
    }

    /**
     * Permette di avere la lista dei contratti di un determinato utente
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @param offset indice iniziale
     * @param pageSize numero elementi
     * @return la view della lista contratti
     */
    @GetMapping("/getListaContratti/{offset}/{pageSize}")
    public String getListaContratti(ModelMap model, @PathVariable("offset") Long offset, @PathVariable("pageSize") Long pageSize){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                List<ContrattiUtenteDTO> listContrattiUtente = contrattoUtenteService.getListaContrattiForUtente(authUser.get(),
                        offset, pageSize);
                model.addAttribute("listaContratti", listContrattiUtente);
                return "ListaContratti";
            }else{
                model.addAttribute("error", "Utente non trovato");
                return "Login";
            }
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (ContrattoUtenteException | ContrattoException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }
    }

    /**
     * Permette di avere i dettagli di un singolo contratto
     * @param model classe contenitore per passare dati tra il controller e la vista
     * @param idContratto id contratto interessato
     * @return la view
     */
    @GetMapping("/getListaContratti/contratto/{id_contratto}")
    public String getSingleContratto(ModelMap model, @PathVariable("id_contratto") Long idContratto){
        try{
            Optional<Utente> authUser = authService.getAuthUtente();
            if(authUser.isPresent()){
                Optional<DettagliContrattoDTO> listContrattiUtente = contrattoUtenteService.getDettagliContratto(authUser.get(),
                        idContratto);
                if(listContrattiUtente.isPresent()){
                    model.addAttribute("contratto", listContrattiUtente.get());
                    model.addAttribute("risultato", "");
                    return "SingoloContratto";
                }else{
                    model.addAttribute("error", "Errore nel reperire contratti");
                    return "redirect:/site/utente/getListaContratti/0/20";
                }

            }else{
                model.addAttribute("error", "Utente non trovato");
                return "Login";
            }
        }catch (UtenteException error){
            model.addAttribute("error", error.getMessage());
            return "Login";
        }catch (ContrattoUtenteException | ContrattoException error){
            model.addAttribute("error", error.getMessage());
            return "Utente";
        }
    }
}
