package com.application.virgo.model;

import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Offerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfferta;

    private Float prezzoProposto;
    private String commento;

    //Mapping per la relazione molti a molti tra utente e offerta
    @OneToMany(mappedBy = "offertaInteressata")
    private Set<OfferteUtente> offerteLegate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_immobile")
    private Immobile idImmobileInteressato;

    public Offerta() {
    }

    public Offerta(Long idOfferta, Float prezzoProposto, String commento, Set<OfferteUtente> offerteLegate) {
        this.idOfferta = idOfferta;
        this.prezzoProposto = prezzoProposto;
        this.commento = commento;
        this.offerteLegate = offerteLegate;
    }

    public Offerta (String commento, Float prezzoProposto) {
        this.prezzoProposto = prezzoProposto;
        this.commento = commento;
    }
}
