package com.application.virgo.DTO.inputDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Dati offerta in input dal front-end
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class InsertOffertaDTO {

    private Long idImmobile;
    //private Long idOfferente;
    private Long idProprietario;

    private Float prezzoProposto;
    private String commento;
    private Boolean inviatoDaProprietario;
}
