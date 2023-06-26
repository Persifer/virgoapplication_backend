package com.application.virgo.model.ComposedRelationship.CompoundKey;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Getter
@Setter
public class ContrattoUtenteCompoundKey implements Serializable {

    @Column(name = "id_venditore")
    private Long idVenditore;

    @Column(name = "id_acquirente")
    private Long idAcquirente;

    @Column(name = "id_contratto")
    private Long idContratto;


}
