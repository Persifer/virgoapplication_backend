package com.application.virgo.model;

import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Set;

@Entity
public class Offerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfferta;

    private Float prezzo_proposto;
    private String commento;

    @OneToMany(mappedBy = "offertaInteressata")
    private Set<OfferteUtente> offerteLegate;

    @ManyToOne
    @JoinColumn(name = "id_immobile")
    private Immobile idImmobileInteressato;

    public Offerta() {
    }

    public Offerta(Long idOfferta, Float prezzo_proposto, String commento, Set<OfferteUtente> offerteLegate) {
        this.idOfferta = idOfferta;
        this.prezzo_proposto = prezzo_proposto;
        this.commento = commento;
        this.offerteLegate = offerteLegate;
    }

    public Long getIdOfferta() {
        return idOfferta;
    }

    public void setIdOfferta(Long idOfferta) {
        this.idOfferta = idOfferta;
    }

    public Float getPrezzo_proposto() {
        return prezzo_proposto;
    }

    public void setPrezzo_proposto(Float prezzo_proposto) {
        this.prezzo_proposto = prezzo_proposto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public Set<OfferteUtente> getOfferteLegate() {
        return offerteLegate;
    }

    public void setOfferteLegate(Set<OfferteUtente> offerteLegate) {
        this.offerteLegate = offerteLegate;
    }
}
