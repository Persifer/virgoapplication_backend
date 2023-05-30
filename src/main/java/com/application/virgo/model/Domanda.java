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

    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente proprietarioCommento;

    @ManyToOne
    @JoinColumn(name="idImmobile")
    private Immobile immobileInteressato;

    public Domanda(String contenuto, LocalDate dataPubblicazione) {
        this.contenuto = contenuto;
        this.dataPubblicazione = dataPubblicazione;
    }
}
