package com.application.virgo.DTO.outputDTO;

import lombok.*;

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

}
