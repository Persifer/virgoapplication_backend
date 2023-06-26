package com.application.virgo.model;

import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Contratto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContratto;

    private Instant dataStipulazione;

    private Float prezzo;

    @OneToMany(mappedBy = "contrattoInteressato")
    private Set<ContrattoUtente> utenteInteressati;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_immobile")
    private Immobile immobileInteressato;


}
