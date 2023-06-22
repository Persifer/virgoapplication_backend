package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListUtentiForProposteDTO {

    private Long idOfferente;

    private String nomeUtente;
    private String cognomeUtente;

    // fatto da peppino :)
    public void ListUtentiForProposteDTO(Long idOfferte, String nomeUtente, String cognomeUtente){
        this.idOfferente = idOfferte;
        this.nomeUtente = nomeUtente;
        this.cognomeUtente = cognomeUtente;
    }


}
