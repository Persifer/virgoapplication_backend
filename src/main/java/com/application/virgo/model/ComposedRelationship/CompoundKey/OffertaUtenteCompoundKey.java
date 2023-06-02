package com.application.virgo.model.ComposedRelationship.CompoundKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode
public class OffertaUtenteCompoundKey implements Serializable {

    @Column(name = "id_utente")
    private Long idProprietario;

    @Column(name = "id_utente")
    private Long idOfferente;

    @Column(name = "id_offerta")
    private Long idOfferta;


}
