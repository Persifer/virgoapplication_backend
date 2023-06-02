package com.application.virgo.model;

import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;


import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@Data
public class Utente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtente;

    private String nome;
    private String cognome;
    @Column(nullable = false)
    private String email;
    private String password;

// ======== RESIDENZA =========
    private String via;
    private String cap;
    private String citta;
    private String provincia;
// ============================

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

    @OneToMany(mappedBy = "utenteInteressato")
    private Set<ContrattoUtente> contrattiUtente;

    @OneToMany(mappedBy = "proprietario")
    private Set<OfferteUtente> offerteRicevute;

    @OneToMany(mappedBy = "offerente")
    private Set<OfferteUtente> offerteProposte;

    @OneToMany(mappedBy = "idDomanda")
    private Set<Domanda> domandeUtente;

    @OneToMany(mappedBy = "idRisposta")
    private Set<Risposta> risposteUtente;

    public Utente() {}
    public Utente(Long idUtente, String nome, String cognome, String email, String password,
                  String via, String cap, String citta, String provincia, LocalDate dataNascita, List<Immobile> immobiliUtente) {
        this.idUtente = idUtente;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.dataNascita = dataNascita;
        this.immobiliUtente = immobiliUtente;
    }
    public Utente(String nome, String cognome, String email, String via,
                     String cap, String citta, String provincia, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.dataNascita = dataNascita;
    }

    public Utente(String nome, String cognome, String email, String password,
                  String via, String cap, String citta, String provincia, LocalDate dataNascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.via = via;
        this.cap = cap;
        this.citta = citta;
        this.provincia = provincia;
        this.dataNascita = dataNascita;
    }



}
