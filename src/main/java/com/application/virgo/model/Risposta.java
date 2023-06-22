package com.application.virgo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Risposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRisposta;

    private String contenuto;
    private Instant dataPubblicazione;

    @Column(columnDefinition = "boolean default false")
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente proprietarioRisposta;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_domanda")
    private Domanda domandaDiRiferimento;

    public Risposta(String contenuto, Instant dataPubblicazione) {
        this.contenuto = contenuto;
        this.dataPubblicazione = dataPubblicazione;
    }
}
