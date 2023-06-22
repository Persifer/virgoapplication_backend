package com.application.virgo.DTO.outputDTO;

import lombok.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetImmobileInfoDTO {

    private String nomeProprietario;
    private String cognomeProprietario;

    private Long idImmobile;
    private String email;

    private LocalDate dataUltimoRestauro;
    private LocalDate dataAcquisizione;
    private LocalDate dataCreazioneImmobile;

    private String descrizione;
    private String titolo;
    private Float prezzo;

    private String locali;
    private String metriQuadri;

// ======== RESIDENZA =========
    private String via;
    private String cap;
    private String citta;
    private String provincia;
// ============================

    private List<DomandaImmobileDTO> listaDomandeImmobile;
    private List<String> listaImmagini;

}
