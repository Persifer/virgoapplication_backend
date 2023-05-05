package com.application.virgo.DTO.inputDTO;

import java.sql.Date;

/*
* Classe creata come Data Transfer Object dato in input per la creazione di un nuovo immobile.
* Al suo interno troviamo, oltre ai dati di un immobile, un campo che memorizza l'id dell'utente
* proprietario del immobile
* */
public class ImmobileDTO {

    private Long idProprietario;

    private Date dataUltimoRestauro;
    private Date dataAcquisizione;
    private Date dataCreazioneImmobile;

    private String descrizione;
    private Float prezzo;

    public ImmobileDTO() {
    }

    public ImmobileDTO(Long idProprietario, Date dataUltimoRestauro, Date dataAcquisizione,
                       String descrizione, Float prezzo) {
        this.idProprietario = idProprietario;
        this.dataUltimoRestauro = dataUltimoRestauro;
        this.dataAcquisizione = dataAcquisizione;
        this.dataCreazioneImmobile = null;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Long getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(Long idProprietario) {
        this.idProprietario = idProprietario;
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
}
