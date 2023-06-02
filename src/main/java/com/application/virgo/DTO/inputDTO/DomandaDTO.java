package com.application.virgo.DTO.inputDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class DomandaDTO {

    @NotNull
    private String contenuto;

    public DomandaDTO() {
    }

    public DomandaDTO(String contenuto) {
        this.contenuto = contenuto;
    }

    public String getContenuto() {
        return contenuto;
    }

    public void setContenuto(String contenuto) {
        this.contenuto = contenuto;
    }
}
