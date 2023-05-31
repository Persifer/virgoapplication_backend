package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetImmobileInfoDTO {

    private String nomeProprietario;
    private String cognomeProprietario;

    private String email;

    private LocalDate dataUltimoRestauro;
    private LocalDate dataAcquisizione;
    private LocalDate dataCreazioneImmobile;

    private String descrizione;
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

}
