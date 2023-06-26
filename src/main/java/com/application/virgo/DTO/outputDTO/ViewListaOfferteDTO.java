package com.application.virgo.DTO.outputDTO;

import lombok.*;
/**
 * Dati lista offerte/proposte di un utente in output dalla business logic
 */
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

    public ViewListaOfferteDTO(String nomeControparte, String cognomeControparte, Long idControparte, Long idImmobile) {
        this.nomeControparte = nomeControparte;
        this.cognomeControparte = cognomeControparte;
        this.idControparte = idControparte;
        this.idImmobile = idImmobile;
    }
}
