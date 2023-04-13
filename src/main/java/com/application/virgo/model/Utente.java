package com.application.virgo.model;

import jakarta.persistence.*;


import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;

    private String nome;
    private String cognome;
    private String email;
    private String password;

// ======== RESIDENZA =========
    private String via;
    private String cap;
    private String citta;
    private String provincia;
// ============================

    private Date dataNascita;

    private String genere;

    @OneToMany(mappedBy = "idImmobile")
    private List<Immobile> immobiliUtente;

    @OneToMany(mappedBy = "utente")
    private Set<RuoloUtente> userRoles;

    public Utente() {}
    public Utente(Long idUtente, String nome, String cognome, String email, String password,
                  String via, String cap, String citta, String provincia, Date dataNascita, String genere, List<Immobile> immobiliUtente) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.dataNascita = dataNascita;
        this.genere = genere;
        this.immobiliUtente = immobiliUtente;
    }

    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
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

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public List<Immobile> getImmobiliUtente() {
        return immobiliUtente;
    }

    public void setImmobiliUtente(List<Immobile> immobiliUtente) {
        this.immobiliUtente = immobiliUtente;
    }
}
