package com.application.virgo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Domanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDomanda;

    private String contenuto;
    private LocalDate dataPubblicazione;

    @Column(columnDefinition = "boolean default false")
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente proprietarioDomanda;

    @ManyToOne
    @JoinColumn(name="idImmobile")
    private Immobile immobileInteressato;

    @OneToOne(mappedBy = "domandaDiRiferimento")
    private Risposta risposta;

    public Domanda(String contenuto, LocalDate dataPubblicazione) {
        this.contenuto = contenuto;
        this.dataPubblicazione = dataPubblicazione;
    }
}
