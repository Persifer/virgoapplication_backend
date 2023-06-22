package com.application.virgo.model;

import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;

    private String nome;
    private String cognome;
    @Column(nullable = false)
    private String email;
    private String password;


    private LocalDate dataNascita;

// ======== PARAMETER =========
    @Column(columnDefinition = "boolean default true")
    private Boolean isEnabled;
    @Column(columnDefinition = "boolean default true")
    private Boolean isAccountNonExpired;
    @Column(columnDefinition = "boolean default true")
    private Boolean isAccountNonLocked;
    @Column(columnDefinition = "boolean default true")
    private Boolean isCredentialsNonExpired;
// ============================



    // Creazione della relazione uno a molti (lato molti) tra l'utente e l'immobile.
    // L'annotazione permettte di dichiarare il tipo di relazione tra le due entità e il tipo di attributo che verrà
    // usato come chiave della relazione. Il parametro mappedBy permette di indicare la variabile che usiamo per rappresentare
    // la classe padre all'interno della nostra classe figlio cioè la variabile della classe immobile (classe figlia) che ci permetterà
    // di raggiungere la classe utente (classe padre) associata a quell'immboile
    @OneToMany(mappedBy = "idImmobile")
    private List<Immobile> immobiliUtente;

    // Creazione della relazione molti a molti (lato uno a molti) tra l'utente e ruolo.
    // L'annotazione permettte di dichiarare il tipo di relazione tra le due entità e il tipo di attributo che verrà
    // usato come chiave della relazione.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "utente_ruolo",
            joinColumns = @JoinColumn(name = "utente_id"),
            inverseJoinColumns = @JoinColumn(name = "ruolo_id")
    )
    private Set<Ruolo> userRole;

// ====================== CONTRATTO ===========================

    @OneToMany(mappedBy = "venditore")
    private Set<ContrattoUtente> contrattiUtenteAsVenditore;

    @OneToMany(mappedBy = "acquirente")
    private Set<ContrattoUtente> contrattiUtenteAsAcquirente;

// ============================================================


// ======================== OFFERTE ===========================

    @OneToMany(mappedBy = "proprietario")
    private Set<OfferteUtente> offerteRicevute;

    @OneToMany(mappedBy = "offerente")
    private Set<OfferteUtente> offerteProposte;

// ============================================================


// ========================== Q&A =============================

    @OneToMany(mappedBy = "idDomanda")
    private Set<Domanda> domandeUtente;

    @OneToMany(mappedBy = "idRisposta")
    private Set<Risposta> risposteUtente;

// ============================================================
    public Utente() {}
    public Utente(Long idUtente, String nome, String cognome, String email, String password,
                  LocalDate dataNascita, List<Immobile> immobiliUtente) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataNascita = dataNascita;
        this.immobiliUtente = immobiliUtente;
    }
    public Utente(String nome, String cognome, String email, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.dataNascita = dataNascita;
    }

    public Utente(String nome, String cognome, String email, String password, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.dataNascita = dataNascita;
    }



}
