package com.application.virgo.model.ComposedRelationship;

import com.application.virgo.model.ComposedRelationship.CompoundKey.ContrattoUtenteCompoundKey;
import jakarta.persistence.*;

import com.application.virgo.model. Utente;
import com.application.virgo.model. Contratto;

@Entity
public class ContrattoUtente {

    @EmbeddedId
    private ContrattoUtenteCompoundKey idContrattoUtente;

    @ManyToOne
    @MapsId("idUtente") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_utente")
    private Utente utenteInteressato;

    @ManyToOne
    @MapsId("idContratto") // serve a mappare la colonna id_utente con la colonna idUtente dell'embeddedId
    @JoinColumn(name="id_contratto")
    private Contratto contrattoInteressato;

    public ContrattoUtente() {
    }

    public ContrattoUtente(ContrattoUtenteCompoundKey idContrattoUtente, Utente utenteContratto, Contratto contrattoUtente) {
        this.idContrattoUtente = idContrattoUtente;
        this.utenteInteressato = utenteContratto;
        this.contrattoInteressato = contrattoUtente;
    }


    public ContrattoUtenteCompoundKey getIdContrattoUtente() {
        return idContrattoUtente;
    }

    public void setIdContrattoUtente(ContrattoUtenteCompoundKey idContrattoUtente) {
        this.idContrattoUtente = idContrattoUtente;
    }

    public Utente getUtenteInteressato() {
        return utenteInteressato;
    }

    public void setUtenteInteressato(Utente utenteContratto) {
        this.utenteInteressato = utenteContratto;
    }

    public Contratto getContrattoInteressato() {
        return contrattoInteressato;
    }

    public void setContrattoInteressato(Contratto contrattoUtente) {
        this.contrattoInteressato = contrattoUtente;
    }
}
