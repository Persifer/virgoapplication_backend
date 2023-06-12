package com.application.virgo.repositories;

import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffertaUtenteJpaRepository extends JpaRepository<OfferteUtente, Long> {

    // Permette di selezionare tutte le offerte da proprietario di un utente
    @Query("SELECT offerta " +
            "FROM OfferteUtente offerta " +
                "JOIN Utente utente ON (offerta.proprietario.idUtente = utente.idUtente)" +
            "WHERE utente.idUtente = :idRequestedUtente")
    public Page<OfferteUtente> getAllOfferteUtenteAsProprietario(Pageable pagable, @Param("idRequestedUtente") Long idProprietario );

    // Permette di selezionare una specifica offerta di un proprietario
    @Query("SELECT offerta " +
            "FROM OfferteUtente offerta " +
                "JOIN Utente utente ON (offerta.proprietario.idUtente = utente.idUtente)" +
            "WHERE utente.idUtente = :idRequestedUtente AND offerta.offertaInteressata.idOfferta = :idRequestedOfferta")
    public Page<OfferteUtente> getSpecificOffertaUtenteAsProprietario(Pageable pagable,
                                                                      @Param("idRequestedUtente") Long idProprietario,
                                                                      @Param("idRequestedOfferta") Long idOfferta );

    @Query("SELECT offerta " +
            "FROM OfferteUtente offerta " +
            "JOIN Utente utente ON (offerta.proprietario.idUtente = utente.idUtente)" +
            "WHERE utente.idUtente = :idRequestedUtente AND offerta.offertaInteressata.idOfferta = :idRequestedOfferta " +
            "AND offerta.offerente.idUtente = :idOfferente")
    public List<OfferteUtente> getAllOfferteBetweenUtenti(
                                                          @Param("idRequestedUtente") Long idProprietario,
                                                          @Param("idOfferente") Long idOfferente);

    // Permette di selezionare tutte le offerte proposte di un determinato utente
    @Query("SELECT offerta " +
            "FROM OfferteUtente offerta " +
            "JOIN Utente utente ON (offerta.offerente.idUtente = utente.idUtente)" +
            "WHERE utente.idUtente = :idRequestedUtente")
    public Page<OfferteUtente> getAllOfferteUtenteAsOfferente(Pageable pagable, @Param("idRequestedUtente") Long idProprietario );

    // Permette di selezionare una specifica offerta proposta ad un utente
    @Query("SELECT offerta " +
            "FROM OfferteUtente offerta " +
            "JOIN Utente utente ON (offerta.offerente.idUtente = utente.idUtente)" +
            "WHERE utente.idUtente = :idRequestedUtente AND offerta.offertaInteressata.idOfferta = :idRequestedOfferta")
    public Page<OfferteUtente> getSpecificOffertaUtenteAsOfferente(Pageable pagable,
                                                                      @Param("idRequestedUtente") Long idProprietario,
                                                                      @Param("idRequestedOfferta") Long idOfferta );

}
