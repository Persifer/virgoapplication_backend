package com.application.virgo.model.ComposedRelationship;

import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import jakarta.persistence.*;

import com.application.virgo.model. Utente;
import com.application.virgo.model. Contratto;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContrattoUtente {

    @EmbeddedId
    private ContrattoUtenteCompoundKey idContrattoUtente;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idVenditore") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_venditore")
    private Utente venditore;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idAcquirente") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_acquirente")
    private Utente acquirente;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("idContratto") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_contratto")
    private Contratto contrattoInteressato;


}
