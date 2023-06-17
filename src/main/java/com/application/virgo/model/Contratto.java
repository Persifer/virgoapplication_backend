package com.application.virgo.model;

import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Contratto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContratto;

    private Instant dataStipulazione;

    private Float prezzo;

    @OneToMany(mappedBy = "contrattoInteressato")
    private Set<ContrattoUtente> utenteInteressati;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_immobile")
    private Immobile immobileInteressato;


}
