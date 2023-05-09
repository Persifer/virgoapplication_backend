package com.application.virgo.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRuolo;

    private String ruolo;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Utente> roleForUser;


    public Ruolo() {
    }

    public Ruolo(Long idRuolo, String ruolo) {
        this.idRuolo = idRuolo;
        this.ruolo = ruolo;
    }

    public Long getIdRuolo() {
        return idRuolo;
    }

    public void setIdRuolo(Long idRuolo) {
        this.idRuolo = idRuolo;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}
