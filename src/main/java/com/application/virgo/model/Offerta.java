package com.application.virgo.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class Offerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idOfferta;

    private Date data_proposta;
    private Date data_accettazione;
    private Date data_declino;

    private Float prezzo_proposto;
    private String commento;

    // Colui che propone un'offerta d'acquisto
    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente idOfferente;

    // Colui che riceve l'offerta di acquisto
    @ManyToOne
    @JoinColumn(name = "id_utente")
    private Utente idBeneficiario;

    public Offerta() {
    }

    public Offerta(Long idOfferta, Date data_proposta, Date data_accettazione, Date data_declino,
                   Float prezzo_proposto, String commento, Utente idOfferente, Utente idBeneficiario) {
        this.idOfferta = idOfferta;
        this.data_proposta = data_proposta;
        this.data_accettazione = data_accettazione;
        this.data_declino = data_declino;
        this.prezzo_proposto = prezzo_proposto;
        this.commento = commento;
        this.idOfferente = idOfferente;
        this.idBeneficiario = idBeneficiario;
    }

    public Long getIdOfferta() {
        return idOfferta;
    }

    public void setIdOfferta(Long idOfferta) {
        this.idOfferta = idOfferta;
    }

    public Date getData_proposta() {
        return data_proposta;
    }

    public void setData_proposta(Date data_proposta) {
        this.data_proposta = data_proposta;
    }

    public Date getData_accettazione() {
        return data_accettazione;
    }

    public void setData_accettazione(Date data_accettazione) {
        this.data_accettazione = data_accettazione;
    }

    public Date getData_declino() {
        return data_declino;
    }

    public void setData_declino(Date data_declino) {
        this.data_declino = data_declino;
    }

    public Float getPrezzo_proposto() {
        return prezzo_proposto;
    }

    public void setPrezzo_proposto(Float prezzo_proposto) {
        this.prezzo_proposto = prezzo_proposto;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public Utente getIdOfferente() {
        return idOfferente;
    }

    public void setIdOfferente(Utente idOfferente) {
        this.idOfferente = idOfferente;
    }

    public Utente getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Utente idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }
}
