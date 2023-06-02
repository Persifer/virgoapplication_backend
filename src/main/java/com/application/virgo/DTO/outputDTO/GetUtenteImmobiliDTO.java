package com.application.virgo.DTO.outputDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUtenteImmobiliDTO {

    private Long idImmobile;
    private LocalDate dataCreazioneImmobile;

    private String descrizione;
    private Float prezzo;

    private String locali;
    private String metriQuadri;

    private Boolean isEnabled;

    private String via;
    private String cap;
    private String citta;
    private String provincia;
}
