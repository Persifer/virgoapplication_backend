package com.application.virgo.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Domanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDomanda;

    private String contenuto;
    private Instant dataPubblicazione;

    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente proprietarioDomanda;

    @ManyToOne
    @JoinColumn(name="idImmobile")
    private Immobile immobileInteressato;

    @OneToOne(mappedBy = "domandaDiRiferimento", fetch = FetchType.EAGER)
    private Risposta risposta;

    public Domanda(String contenuto, Instant dataPubblicazione) {
        this.contenuto = contenuto;
        this.dataPubblicazione = dataPubblicazione;
    }
}
