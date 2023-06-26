package com.application.virgo.DTO.inputDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

/**
 * Dati rispota in input dal front-end
 */
public class RispostaDTO {

    @NotNull
    private String contenuto;

    public RispostaDTO() {
    }

    public RispostaDTO(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
}
