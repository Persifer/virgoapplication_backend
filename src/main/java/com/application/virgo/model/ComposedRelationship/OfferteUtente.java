package com.application.virgo.model.ComposedRelationship;

import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import com.application.virgo.model.ComposedRelationship.CompoundKey.OffertaUtenteCompoundKey;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class OfferteUtente {

    @EmbeddedId
    private OffertaUtenteCompoundKey idOffertaUtente;

    @ManyToOne
    @MapsId("idUtente") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_utente")
    private Utente utenteOfferta;

    @ManyToOne
    @MapsId("idOfferta") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_offerta")
    private Offerta offertaInteressata;

    private Date data_proposta;

    private Date data_accettazione;
    private Date data_declino;

    private Boolean isAccettato;
    private Boolean isEnabled;
    private Boolean isOfferente;

    public OfferteUtente() {
    }
}
