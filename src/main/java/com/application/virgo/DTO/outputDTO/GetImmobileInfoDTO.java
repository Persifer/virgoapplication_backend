package com.application.virgo.DTO.outputDTO;

import java.sql.Date;

public class GetImmobileInfoDTO {

    private String nomeProprietario;
    private String cognomeProprietario;

    private Date dataUltimoRestauro;
    private Date dataAcquisizione;
    private Date dataCreazioneImmobile;

    private String descrizione;
    private Float prezzo;

    public GetImmobileInfoDTO(String nomeProprietario, String cognomeProprietario, Date dataUltimoRestauro,
                              Date dataAcquisizione, Date dataCreazioneImmobile, String descrizione, Float prezzo) {
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
