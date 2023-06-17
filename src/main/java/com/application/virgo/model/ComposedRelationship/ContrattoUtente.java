package com.application.virgo.model.ComposedRelationship;

import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import jakarta.persistence.*;

import com.application.virgo.model. Utente;
import com.application.virgo.model. Contratto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ContrattoUtente {

    @EmbeddedId
    private ContrattoUtenteCompoundKey idContrattoUtente;

    @ManyToOne
    @MapsId("idVenditore") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_venditore")
    private Utente venditore;

    @ManyToOne
    @MapsId("idAcquirente") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_acquirente")
    private Utente acquirente;

    @ManyToOne
    @MapsId("idContratto") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_contratto")
    private Contratto contrattoInteressato;


}
