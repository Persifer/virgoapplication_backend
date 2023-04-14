package com.application.virgo.model.ComposedRelationship.CompoundKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OffertaUtenteCompoundKey implements Serializable {

    @Column(name = "id_utente")
    private Long idUtente;

    @Column(name = "id_offerta")
    private Long idOfferta;

    public OffertaUtenteCompoundKey() {
    }

    public OffertaUtenteCompoundKey(Long idUtente, Long idOfferta) {
        this.idUtente = idUtente;
        this.idOfferta = idOfferta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffertaUtenteCompoundKey that = (OffertaUtenteCompoundKey) o;
        return idUtente.equals(that.idUtente) && idOfferta.equals(that.idOfferta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUtente, idOfferta);
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public Long getIdOfferta() {
        return idOfferta;
    }

    public void setIdOfferta(Long idOfferta) {
        this.idOfferta = idOfferta;
    }
}
