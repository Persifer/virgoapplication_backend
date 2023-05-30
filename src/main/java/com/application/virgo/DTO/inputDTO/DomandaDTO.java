package com.application.virgo.DTO.inputDTO;

public class DomandaDTO {

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
