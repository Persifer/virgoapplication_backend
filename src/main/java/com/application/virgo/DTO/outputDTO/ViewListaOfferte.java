package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewListaOfferte {

    private String nomeOfferente;
    private String cognomeOfferente;

    //private String dataUltimoUpdate;

    private Long idOfferente;
    private Long idImmobile;

}
