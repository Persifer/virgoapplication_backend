package com.application.virgo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Immobile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idImmobile;

    private LocalDate dataUltimoRestauro;
    private LocalDate dataAcquisizione;
    private LocalDate dataCreazioneImmobile;
    private String descrizione;

    private String locali;
    private String metriQuadri;
    private Float prezzo;

// ======== RESIDENZA =========
    private String via;
    private String cap;
    private String citta;
    private String provincia;
// ============================

    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled;

    @ManyToOne
    @JoinColumn(name="idUtente")
    private Utente proprietario;

    // Proposte in cui è presente il singolo immobile
    @OneToMany(mappedBy = "idImmobileInteressato")
    private Set<Offerta> proposteLegateImmobile;

    @OneToOne(mappedBy = "immobileInteressato")
    private Contratto contratto;

    @OneToMany(mappedBy = "idDomanda")
    private Set<Domanda> domandeImmobile;



}
