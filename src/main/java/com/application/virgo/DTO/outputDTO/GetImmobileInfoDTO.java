package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetImmobileInfoDTO {

    private String nomeProprietario;
    private String cognomeProprietario;

    private LocalDate dataUltimoRestauro;
    private LocalDate dataAcquisizione;
    private LocalDate dataCreazioneImmobile;

    private String descrizione;
    private Float prezzo;

// ======== RESIDENZA =========
    private String via;
    private String cap;
    private String citta;
    private String provincia;
// ============================

}
