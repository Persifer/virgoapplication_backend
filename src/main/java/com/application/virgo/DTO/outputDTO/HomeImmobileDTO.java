package com.application.virgo.DTO.outputDTO;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HomeImmobileDTO {

    private Long idImmobile;

    private String descrizione;
    private String titolo;

    private Float prezzo;

    private String locali;
    private String metriQuadri;

    private String immagineImmobile;


}
