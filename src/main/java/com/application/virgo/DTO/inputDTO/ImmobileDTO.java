package com.application.virgo.DTO.inputDTO;

import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.time.LocalDate;

/*
* Classe creata come Data Transfer Object dato in input per la creazione di un nuovo immobile.
* Al suo interno troviamo, oltre ai dati di un immobile, un campo che memorizza l'id dell'utente
* proprietario del immobile
* */
public class ImmobileDTO {

    private Long idProprietario;

    @NotNull
    private LocalDate dataUltimoRestauro;
    @NotNull
    private LocalDate dataAcquisizione;
    private LocalDate dataCreazioneImmobile;

    private String descrizione;
    @NotNull
    private Float prezzo;

    public ImmobileDTO() {
    }

    public ImmobileDTO(Long idProprietario, LocalDate dataUltimoRestauro, LocalDate dataAcquisizione,
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

    public LocalDate getDataUltimoRestauro() {
        return dataUltimoRestauro;
    }

    public void setDataUltimoRestauro(LocalDate dataUltimoRestauro) {
        this.dataUltimoRestauro = dataUltimoRestauro;
    }

    public LocalDate getDataAcquisizione() {
        return dataAcquisizione;
    }

    public void setDataAcquisizione(LocalDate dataAcquisizione) {
        this.dataAcquisizione = dataAcquisizione;
    }

    public LocalDate getDataCreazioneImmobile() {
        return dataCreazioneImmobile;
    }

    public void setDataCreazioneImmobile(LocalDate dataCreazioneImmobile) {
        this.dataCreazioneImmobile = dataCreazioneImmobile;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
}
