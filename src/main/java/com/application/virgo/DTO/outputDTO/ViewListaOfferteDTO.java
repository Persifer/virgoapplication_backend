package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewListaOfferteDTO {

    private String nomeOfferente;
    private String cognomeOfferente;

    //private String dataUltimoUpdate;

    private Long idOfferente;
    private Long idImmobile;

}
