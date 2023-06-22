package com.application.virgo.repositories;

import com.application.virgo.DTO.outputDTO.ListUnviewMessageDTO;
import com.application.virgo.model.ComposedRelationship.CompoundKey.OffertaUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OffertaUtenteJpaRepository extends JpaRepository<OfferteUtente, OffertaUtenteCompoundKey> {

    // Permette di selezionare tutte le offerte ricevute raggrupate per utente che ha inviato l'offerta
    @Query(value = "SELECT offerte_utente.id_offerente" +
            " FROM offerte_utente" +
            " JOIN offerta ON (offerte_utente.id_offerta = offerta.id_offerta)" +
            " WHERE offerta.id_immobile IN (" +
            "    SELECT immobile.id_immobile" +
            "    FROM immobile" +
            "        JOIN utente ON (immobile.id_utente = utente.id_utente)" +
            "    WHERE utente.id_utente = :idRequestedUtente)" +
            "GROUP BY offerte_utente.id_offerente;", nativeQuery = true)
    public List<Long> getAllOfferteUtenteAsProprietario(@Param("idRequestedUtente") Long idProprietario );


    // Permette di selezionare tutte le offerte inviate e ricevute tra il proprietario dell'immobile ed un possibile acquirente
    @Query("SELECT offerta " +
            "FROM OfferteUtente offerta " +
                "JOIN Utente utente ON (offerta.proprietario.idUtente = utente.idUtente)" +
                "JOIN Offerta offer ON (offerta.offertaInteressata.idOfferta = offer.idOfferta)" +
                "JOIN Immobile immobile ON (offer.idImmobileInteressato.idImmobile = immobile.idImmobile)" +
                "JOIN Utente user ON (immobile.proprietario.idUtente = user.idUtente)" +
            "WHERE utente.idUtente = :idOfferente AND offerta.proprietario.idUtente = :idRequestedUtente " +
            "AND immobile.idImmobile = :idImmobile AND user.idUtente=:idRequestedUtente")
    public List<OfferteUtente> getAllOfferteBetweenUtenti(
                                                          @Param("idRequestedUtente") Long idProprietario,
                                                          @Param("idOfferente") Long idOfferente,
                                                          @Param("idImmobile") Long idImmobile);

    // Permette di selezionare tutte le offerte proposte di un determinato utente
    @Query("SELECT offerta.proprietario.idUtente " +
            "FROM OfferteUtente offerta " +
            "JOIN Utente utente ON (offerta.offerente.idUtente = utente.idUtente)" +
            "WHERE utente.idUtente = :idRequestedUtente " +
                "GROUP BY offerta.proprietario.idUtente")
    public List<Long> getAllOfferteUtenteAsOfferente(@Param("idRequestedUtente") Long idOfferente);

    @Query(value = "select immobile.id_immobile from offerte_utente " +
            "join offerta on (offerte_utente.id_offerta = offerta.id_offerta) " +
            "join immobile on (offerta.id_immobile = immobile.id_immobile) " +
            "where offerte_utente.id_proprietario = :idProprietario " +
            "and offerte_utente.id_offerente=:idOfferente " +
            "group by immobile.id_immobile;", nativeQuery = true)
    public List<Long> getImmobiliInteressati(@Param("idOfferente") Long idOfferente, @Param("idProprietario") Long idProrietario);


    /*

    SELECT utente.email, COUNT(offerte_utente.id_proprietario)
    FROM offerte_utente
        JOIN utente ON (offerte_utente.id_proprietario = utente.id_utente)
    WHERE offerte_utente.visionata_da_propietario = 0 GROUP BY(offerte_utente.id_proprietario);
     */
    @Query("SELECT utente.email, COUNT(offerta.proprietario.idUtente) " +
            "FROM OfferteUtente offerta " +
                "JOIN Utente utente ON (offerta.proprietario.idUtente = utente.idUtente)" +
            "WHERE offerta.visionataDaPropietario = false " +
            "GROUP BY offerta.proprietario.idUtente")
    public List<ListUnviewMessageDTO> getListUtenteWithUnreadMessages();


    public Optional<OfferteUtente> getOfferteUtenteByProprietarioAndOffertaInteressata(Utente utente, Offerta offerta);

    @Query(
            "SELECT offerta " +
            "FROM OfferteUtente offerta " +
                    "JOIN Offerta offer ON (offerta.offertaInteressata.idOfferta = offer.idOfferta)" +
                    "JOIN Immobile immobile ON (offer.idImmobileInteressato.idImmobile = immobile.idImmobile )" +
            "WHERE immobile.idImmobile = :idReqImmobile AND offerta.offerente.idUtente != :idOfferente"
    )
    public List<OfferteUtente> getListOfferteRelatedToImmobile(@Param("idReqImmobile") Long idImmobile,
                                                               @Param("idOfferente") Long idOfferente);

}
