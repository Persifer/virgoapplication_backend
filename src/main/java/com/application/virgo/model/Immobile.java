package com.application.virgo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.sql.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
public class Immobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImmobile;

    private Date dataUltimoRestauro;
    private Date dataAcquisizione;
    private Date dataCreazioneImmobile;
    private String descrizione;

    private Float prezzo;

    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente proprietario;

    // Proposte in cui è presente il singolo immobile
    @OneToMany(mappedBy = "idImmobileInteressato")
    private Set<Offerta> proposteLegateImmobile;

    @OneToOne(mappedBy = "immobileInteressato")
    private Contratto contratto;

    public Immobile() {
    }

    public Immobile(Date dataUltimoRestauro, Date dataAcquisizione, Date dataCreazioneImmobile, String descrizione, Float prezzo) {
        this.dataUltimoRestauro = dataUltimoRestauro;
        this.dataAcquisizione = dataAcquisizione;
        this.dataCreazioneImmobile = dataCreazioneImmobile;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    public Long getIdImmobile() {
        return idImmobile;
    }

    public void setIdImmobile(Long idImmobile) {
        this.idImmobile = idImmobile;
    }

    public Date getDataUltimoRestauro() {
        return dataUltimoRestauro;
    }

    public void setDataUltimoRestauro(Date dataUltimoRestauro) {
        this.dataUltimoRestauro = dataUltimoRestauro;
    }

    public Date getDataAcquisizione() {
        return dataAcquisizione;
    }

    public void setDataAcquisizione(Date dataAcquisizione) {
        this.dataAcquisizione = dataAcquisizione;
    }

    public Date getDataCreazioneImmobile() {
        return dataCreazioneImmobile;
    }

    public void setDataCreazioneImmobile(Date dataCreazioneImmobile) {
        this.dataCreazioneImmobile = dataCreazioneImmobile;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Set<Offerta> getProposteLegateImmobile() {
        return proposteLegateImmobile;
    }

    public void setProposteLegateImmobile(Set<Offerta> proposteLegateImmobile) {
        this.proposteLegateImmobile = proposteLegateImmobile;
    }

    public Contratto getContratto() {
        return contratto;
    }

    public void setContratto(Contratto contratto) {
        this.contratto = contratto;
    }
}
