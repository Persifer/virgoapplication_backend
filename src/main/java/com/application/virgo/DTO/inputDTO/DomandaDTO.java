package com.application.virgo.DTO.inputDTO;

import com.application.virgo.model.Utente;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Dati domanda in input dal front-end
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DomandaDTO {

    @NotNull
    private String contenuto;

    @NotNull
    private Long idImmobile;
}
