package com.application.virgo.DTO.inputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class InsertOffertaDTO {

    private Long idImmobile;
    //private Long idOfferente;
    private Long idProprietario;

    private Float prezzoProposto;
    private String commento;
}