package com.application.virgo.DTO;

import com.application.virgo.model.Immobile;
import lombok.AllArgsConstructor;

import java.sql.Date;
import java.util.List;


public class UtenteDTO {

    private String nome;
    private String cognome;
    //@Column(nullable = false)
    private String email;
    private String password;

    // ======== RESIDENZA =========
    private String via;
    private String cap;
    private String citta;
    private String provincia;
// ============================

    private Date dataNascita;

    public UtenteDTO(String nome, String cognome, String email, String via,
                     String cap, String citta, String provincia, Date dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.dataNascita = dataNascita;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }
}
