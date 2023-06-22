package com.application.virgo.model.ComposedRelationship.CompoundKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class OffertaUtenteCompoundKey implements Serializable {

    @Column(name = "id_utente")
    private Long idProprietario;

    @Column(name = "id_utente")
    private Long idOfferente;

    @Column(name = "id_offerta")
    private Long idOfferta;


}
