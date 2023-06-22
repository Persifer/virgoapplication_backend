package com.application.virgo.model.ComposedRelationship;

import com.application.virgo.model.ComposedRelationship.CompoundKey.OffertaUtenteCompoundKey;
import com.application.virgo.model.Offerta;
import com.application.virgo.model.Utente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@AllArgsConstructor
@Getter
@Setter
public class OfferteUtente {

    @EmbeddedId
    private OffertaUtenteCompoundKey idOffertaUtente;

    @ManyToOne
    @MapsId("idProprietario") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_proprietario", insertable = false, updatable = false)
   // @Column(insertable=false, updatable=false)
    // proprietario è colui a cui è stata fatta l'offerta
    private Utente proprietario;

    @ManyToOne
    @MapsId("idOfferente") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_offerente", insertable = false, updatable = false)
    //@Column(insertable=false, updatable=false)
    // colui che ha proposto l'offerta
    private Utente offerente;

    @ManyToOne
    @MapsId("idOfferta") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_offerta")
    private Offerta offertaInteressata;

    private Instant data_proposta;

    private Boolean madeByProprietario;

    private Instant data_accettazione;
    private Instant data_declino;


    private Boolean isAccettato;
    private Boolean isDeclinato;

    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled;

    @Column(columnDefinition = "boolean default false")
    private Boolean visionataDaPropietario;

    public OfferteUtente() {
    }

    public OfferteUtente(OffertaUtenteCompoundKey idOffertaUtente, Utente proprietario, Utente offerente, Offerta offertaInteressata) {

        this.idOffertaUtente = idOffertaUtente;

        this.proprietario = proprietario;
        this.offerente = offerente;

        this.offertaInteressata = offertaInteressata;

        this.data_proposta = Instant.now();
        this.data_accettazione = null;
        this.data_declino = null;

        this.isAccettato = null;
        this.isDeclinato = null;

    }
}
