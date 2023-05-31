package com.application.virgo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Risposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRisposta;

    private String contenuto;
    private LocalDate dataPubblicazione;

    @Column(columnDefinition = "boolean default false")
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente proprietarioRisposta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_domanda")
    private Domanda domandaDiRiferimento;
}
