package com.application.virgo.DTO.outputDTO;

import java.sql.Date;
import java.time.LocalDate;

public class GetImmobileInfoDTO {

    private String nomeProprietario;
    private String cognomeProprietario;

    private LocalDate dataUltimoRestauro;
    private LocalDate dataAcquisizione;
    private LocalDate dataCreazioneImmobile;

    private String descrizione;
    private Float prezzo;

    public GetImmobileInfoDTO(String nomeProprietario, String cognomeProprietario, LocalDate dataUltimoRestauro,
                              LocalDate dataAcquisizione, LocalDate dataCreazioneImmobile, String descrizione, Float prezzo) {
        this.nomeProprietario = nomeProprietario;
        this.cognomeProprietario = cognomeProprietario;
        this.dataUltimoRestauro = dataUltimoRestauro;
        this.dataAcquisizione = dataAcquisizione;
        this.dataCreazioneImmobile = dataCreazioneImmobile;
        this.descrizione = descrizione;
        this.prezzo = prezzo;
    }

    public GetImmobileInfoDTO() {

    }

    public String getNomeProprietario() {

        return nomeProprietario;
    }

    public void setNomeProprietario(String nomeProprietario) {
        this.nomeProprietario = nomeProprietario;
    }

    public String getCognomeProprietario() {
        return cognomeProprietario;
    }

    public void setCognomeProprietario(String cognomeProprietario) {
        this.cognomeProprietario = cognomeProprietario;
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

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }
}
