package com.application.virgo.DTO.outputDTO;

import lombok.*;
/**
 * Dati singola offerta/proposta di un utente in output dalla business logic
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViewOfferteBetweenUtentiDTO {

    private Long idImmobile;

    private String nomeOfferente;
    private String cognomeOfferente;

    private Boolean isDeclinato;
    private String dataDeclino;
    private Boolean isAccettato;
    private String dataAccettazione;

    private String dataOfferta;
    private String prezzo;
    private String commento;

    private Long idOfferta;
    private Long idPropietario;
    private Long idOfferente;

    private Boolean inviatoDaProprietario;

}
