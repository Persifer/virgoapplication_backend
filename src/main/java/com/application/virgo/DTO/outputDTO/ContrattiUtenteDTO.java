package com.application.virgo.DTO.outputDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContrattiUtenteDTO {

    private String nomeAcquirente;
    private String cognomeAcquirente;

    private String dataStipulazione;
    private String idImmobile;

    private Long idContratto;


}
