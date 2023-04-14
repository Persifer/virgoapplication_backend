package com.application.virgo.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class Immobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImmobile;

    private Date dataUltimoRestauro;
    private Date dataAcquisizione;
    private Date dataCreazioneImmobile;

    private Float prezzo;

    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente idProprietario;

    // Proposte in cui è presente il singolo immobile
    @OneToMany(mappedBy = "idImmobileInteressato")
    private Set<Offerta> proposteLegateImmobile;

    @OneToOne(mappedBy = "immobileInteressato")
    private Contratto contratto;

    public Immobile() {
    }

    public Immobile(Long idImmobile, Date dataUltimoRestauro, Date dataAcquisizione, Date dataCreazioneImmobile, Float prezzo, Utente idProprietario) {
        this.idImmobile = idImmobile;
        this.dataUltimoRestauro = dataUltimoRestauro;
        this.dataAcquisizione = dataAcquisizione;
        this.dataCreazioneImmobile = dataCreazioneImmobile;
        this.prezzo = prezzo;
        this.idProprietario = idProprietario;
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

    public Utente getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(Utente idProprietario) {
        this.idProprietario = idProprietario;
    }
}
