package com.application.virgo.model;

import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class Contratto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContratto;

    private String identificativoContratto;

    private Date dataStipulazione;

    private Float prezzo;

    @OneToMany(mappedBy = "contrattoInteressato")
    private Set<ContrattoUtente> utenteInteressati;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_immobile")
    private Immobile immobileInteressato;

    public Contratto() {
    }

    public Contratto(Long idContratto, String identificativoContratto, Date dataStipulazione, Float prezzo) {
        this.idContratto = idContratto;
        this.identificativoContratto = identificativoContratto;
        this.dataStipulazione = dataStipulazione;
        this.prezzo = prezzo;
    }

    public Long getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(Long idContratto) {
        this.idContratto = idContratto;
    }

    public String getIdentificativoContratto() {
        return identificativoContratto;
    }

    public void setIdentificativoContratto(String identificativoContratto) {
        this.identificativoContratto = identificativoContratto;
    }

    public Date getDataStipulazione() {
        return dataStipulazione;
    }

    public void setDataStipulazione(Date dataStipulazione) {
        this.dataStipulazione = dataStipulazione;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
}
