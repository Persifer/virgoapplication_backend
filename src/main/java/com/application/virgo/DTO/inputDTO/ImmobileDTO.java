package com.application.virgo.DTO.inputDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

/*
* Classe creata come Data Transfer Object dato in input per la creazione di un nuovo immobile.
* Al suo interno troviamo, oltre ai dati di un immobile, un campo che memorizza l'id dell'utente
* proprietario del immobile
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImmobileDTO {

    private LocalDate dataUltimoRestauro;
    @NotNull
    private LocalDate dataAcquisizione;
    private LocalDate dataCreazioneImmobile;

    private String descrizione;
    private String titolo;

    @NotNull
    private Float prezzo;
    @Pattern(regexp = "[1-9]*")
    @NotNull
    private String locali;
    @Pattern(regexp = "[0-9]*")
    @NotNull
    private String metriQuadri;

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

    private Boolean isEnabled;
}
