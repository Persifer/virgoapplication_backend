package com.application.virgo.DTO.outputDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContrattiUtenteDTO {

    private String nomeAcquirente;
    private String cognomeAcquirente;

    private String dataStipulazione;
    private String idImmobile;


}
