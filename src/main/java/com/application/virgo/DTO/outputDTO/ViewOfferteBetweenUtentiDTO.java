package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewOfferteBetweenUtentiDTO {

    private Long idImmobile;

    private String nomeOfferente;
    private String cognomeOfferente;

    private Boolean isDeclinato;
    private String dataDeclino;
    private Boolean isAccettato;
    private String dataAccettazione;

    private String dataOfferta;
    private String prezzo;

    private Long idOfferta;
    private Long idPropietario;
    private Long idOfferente;


}