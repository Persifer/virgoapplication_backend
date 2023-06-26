package com.application.virgo.DTO.outputDTO;

import lombok.*;
/**
 * Dati singolo contratto in output dalla business logic
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DettagliContrattoDTO {

    private String nomeControparte;
    private String cognomeControparte;
    private Float prezzoDelContratto;
    private String dataStipulazioneContratto;

    private String titoloImmobile;
    private Long idImmobile;

    private Long idContratto;

}
