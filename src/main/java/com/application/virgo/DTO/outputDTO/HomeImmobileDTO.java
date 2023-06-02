package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class HomeImmobileDTO {

    private Long idImmobile;

    private String descrizione;
    private String titolo;

    private Float prezzo;

    private String locali;
    private String metriQuadri;


}
