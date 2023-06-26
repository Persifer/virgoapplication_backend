package com.application.virgo.DTO.outputDTO;

import lombok.*;
/**
 * Dati lista utenti che hanno fatto una proposta in output dalla business logic
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ListUtentiForProposteDTO {

    private Long idOfferente;

    private String nomeUtente;
    private String cognomeUtente;

    public void ListUtentiForProposteDTO(Long idOfferte, String nomeUtente, String cognomeUtente){
        this.idOfferente = idOfferte;
        this.nomeUtente = nomeUtente;
        this.cognomeUtente = cognomeUtente;
    }


}
