package com.application.virgo.DTO.inputDTO;

import com.application.virgo.model.Immobile;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class UtenteDTO {

    @NotNull
    @Pattern(regexp = "[A-z]")
    private String nome;
    @NotNull
    @Pattern(regexp = "[A-z]")
    private String cognome;
    @NotNull
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@+[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$")
    private String email;
    @NotNull
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d#@$!%*?&]{6,16}$")
    private String password;

// ======== RESIDENZA =========
    @NotNull
    private String via;
    @NotNull
    @Pattern(regexp = "[0-9]{6}")
    private String cap;
    @NotNull
    private String citta;
    @NotNull
    @Pattern(regexp = "[A-Z]{2}")
    private String provincia;
// ============================

    private LocalDate dataNascita;

    public UtenteDTO(String nome, String cognome, String email, String via,
                     String cap, String citta, String provincia, LocalDate dataNascita) {
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

    public LocalDate getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(LocalDate dataNascita) {
        this.dataNascita = dataNascita;
    }
}
