package com.application.virgo.model.ComposedRelationship.CompoundKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ContrattoUtenteCompoundKey implements Serializable {

    @Column(name = "id_utente")
    private Integer idUtente;

    @Column(name = "id_contratto")
    private Integer idContratto;

    public ContrattoUtenteCompoundKey() {
    }

    public ContrattoUtenteCompoundKey(Integer idUtente, Integer idContratto) {
        this.idUtente = idUtente;
        this.idContratto = idContratto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContrattoUtenteCompoundKey that = (ContrattoUtenteCompoundKey) o;
        return idUtente.equals(that.idUtente) && idContratto.equals(that.idContratto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtente, idContratto);
    }

    public Integer getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Integer idUtente) {
        this.idUtente = idUtente;
    }

    public Integer getIdContratto() {
        return idContratto;
    }

    public void setIdContratto(Integer idContratto) {
        this.idContratto = idContratto;
    }
}
