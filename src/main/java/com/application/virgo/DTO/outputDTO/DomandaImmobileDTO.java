package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DomandaImmobileDTO {

    private String nomeProprietario;
    private String cognomeProprietario;

    private String contenuto;
    private String dataPubblicazione;


}
