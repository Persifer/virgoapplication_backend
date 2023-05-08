package com.application.virgo.model;

import com.application.virgo.model.ComposedRelationship.ContrattoUtente;
import com.application.virgo.model.ComposedRelationship.OfferteUtente;
import com.application.virgo.model.ComposedRelationship.RuoloUtente;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.boot.context.properties.bind.DefaultValue;


import java.sql.Date;
import java.util.List;
import java.util.Set;

@Entity
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

    private Date dataNascita;


    // Creazione della relazione uno a molti (lato molti) tra l'utente e l'immobile.
    // L'annotazione permettte di dichiarare il tipo di relazione tra le due entità e il tipo di attributo che verrà
    // usato come chiave della relazione. Il parametro mappedBy permette di indicare la variabile che usiamo per rappresentare
    // la classe padre all'interno della nostra classe figlio cioè la variabile della classe immobile (classe figlia) che ci permetterà
    // di raggiungere la classe utente (classe padre) associata a quell'immboile
    @OneToMany(mappedBy = "idImmobile")
    private List<Immobile> immobiliUtente;

    // Creazione della relazione molti a molti (lato uno a molti) tra l'utente e ruolo.
    // L'annotazione permettte di dichiarare il tipo di relazione tra le due entità e il tipo di attributo che verrà
    // usato come chiave della relazione. I
    @OneToMany(mappedBy = "utente")
    private Set<RuoloUtente> userRoles;

    @OneToMany(mappedBy = "utenteInteressato")
    private Set<ContrattoUtente> contrattiUtente;

    @OneToMany(mappedBy = "utenteOfferta")
    private Set<OfferteUtente> offerteUtente;

    public Utente() {}
    public Utente(Long idUtente, String nome, String cognome, String email, String password,
                  String via, String cap, String citta, String provincia, Date dataNascita, List<Immobile> immobiliUtente) {
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
                     String cap, String citta, String provincia, Date dataNascita) {
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
                  String via, String cap, String citta, String provincia, Date dataNascita) {
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


    public Long getIdUtente() {
        return idUtente;
    }

    public void setIdUtente(Long idUtente) {
        this.idUtente = idUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVia() {
        return via;
    }

    public void setVia(String via) {
        this.via = via;
    }

    public String getCap() {
        return cap;
    }

    public void setCap(String cap) {
        this.cap = cap;
    }

    public String getCitta() {
        return citta;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Date getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(Date dataNascita) {
        this.dataNascita = dataNascita;
    }

    public List<Immobile> getImmobiliUtente() {
        return immobiliUtente;
    }

    public void setImmobiliUtente(List<Immobile> immobiliUtente) {
        this.immobiliUtente = immobiliUtente;
    }


}
