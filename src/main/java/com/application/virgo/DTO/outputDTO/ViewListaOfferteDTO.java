package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewListaOfferteDTO {

    private String nomeControparte;
    private String cognomeControparte;

    //private String dataUltimoUpdate;

    private Long idControparte;
    private Long idImmobile;

}
