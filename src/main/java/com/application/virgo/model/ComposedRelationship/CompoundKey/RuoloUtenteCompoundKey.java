package com.application.virgo.model.ComposedRelationship.CompoundKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

/*
* La classe in questione si occupa di gestire le relazioni molti a molti e, nello specifico,
* */
@Embeddable
public class RuoloUtenteCompoundKey implements Serializable {

    @Column(name = "id_utente")
    private Long idUtente;

    @Column(name = "id_ruolo")
    private Long idRuolo;

    public RuoloUtenteCompoundKey(Long idUtente, Long idRuolo) {
        this.idUtente = idUtente;
        this.idRuolo = idRuolo;
    }

    public RuoloUtenteCompoundKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuoloUtenteCompoundKey that = (RuoloUtenteCompoundKey) o;
        return Objects.equals(idUtente, that.idUtente) && Objects.equals(idRuolo, that.idRuolo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtente, idRuolo);
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public Long getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(Long idRuolo) {
        this.idRuolo = idRuolo;
    }
}
