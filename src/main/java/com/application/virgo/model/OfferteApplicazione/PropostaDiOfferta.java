package com.application.virgo.model.OfferteApplicazione;

import com.application.virgo.model.Immobile;
import com.application.virgo.model.Utente;
import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class PropostaDiOfferta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPropostaDiOfferta;

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
    @JoinColumn(name = "id_immobile")
    private Immobile idImmobileProposta;

    public PropostaDiOfferta() {
    }

    public PropostaDiOfferta(Long idOfferta, Date data_proposta, Date data_accettazione, Date data_declino,
                             Float prezzo_proposto, String commento, Utente idOfferente, Immobile idBeneficiario) {
        this.idPropostaDiOfferta = idOfferta;
        this.data_proposta = data_proposta;
        this.data_accettazione = data_accettazione;
        this.data_declino = data_declino;
        this.prezzo_proposto = prezzo_proposto;
        this.commento = commento;
        this.idOfferente = idOfferente;
        this.idImmobileProposta = idBeneficiario;
    }

    public Long getIdPropostaDiOfferta() {
        return idPropostaDiOfferta;
    }

    public void setIdPropostaDiOfferta(Long idOfferta) {
        this.idPropostaDiOfferta = idOfferta;
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

    public Immobile getIdImmobileProposta() {
        return idImmobileProposta;
    }

    public void setIdImmobileProposta(Immobile idBeneficiario) {
        this.idImmobileProposta = idBeneficiario;
    }
}
