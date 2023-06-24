package com.application.virgo.DTO.outputDTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewListaOfferteDTO {

    private String nomeControparte;
    private String cognomeControparte;

    //private String dataUltimoUpdate;

    private Long idControparte;
    private Long idImmobile;

    private String titoloImmobile;

}
