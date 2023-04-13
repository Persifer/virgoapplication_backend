package com.application.virgo.model;

import com.application.virgo.model.CompoundKey.RuoloUtenteCompoundKey;
import jakarta.persistence.*;
import org.springframework.data.repository.cdi.Eager;

@Entity
public class RuoloUtente {

    @EmbeddedId
    private RuoloUtenteCompoundKey idRuoloUtente;

    @ManyToOne
    @MapsId("idUtente") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_utente")
    private Utente utente;

    @ManyToOne
    @MapsId("idRuolo") // serve a mappare la colonna id_ruolo con la colonna idRuolo dell'embeddedId
    @JoinColumn(name="id_ruolo")
    private Ruolo ruolo;

    public RuoloUtente() {
    }

    public RuoloUtente(RuoloUtenteCompoundKey idRuoloUtente, Utente utente, Ruolo ruolo) {
        this.idRuoloUtente = idRuoloUtente;
        this.utente = utente;
        this.ruolo = ruolo;
    }

    public RuoloUtenteCompoundKey getIdRuoloUtente() {
        return idRuoloUtente;
    }

    public void setIdRuoloUtente(RuoloUtenteCompoundKey idRuoloUtente) {
        this.idRuoloUtente = idRuoloUtente;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Ruolo getRuolo() {
        return ruolo;
    }

    public void setRuolo(Ruolo ruolo) {
        this.ruolo = ruolo;
    }
}
