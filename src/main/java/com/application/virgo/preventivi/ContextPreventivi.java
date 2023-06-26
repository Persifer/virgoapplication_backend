package com.application.virgo.preventivi;

import com.application.virgo.preventivi.implementation.AlgoritmoAziendaXYZ;
import com.application.virgo.preventivi.interfaccia.AlgoritmoDiPreventivo;

/**
 * Creazione del contesto per lo strategy pattern
 */
public class ContextPreventivi {

    private AlgoritmoDiPreventivo algoritmoDiPreventivo;

    public void setAlgoritmo(AlgoritmoDiPreventivo algoritmo){
        // instanzio l'algoritmo
        this.algoritmoDiPreventivo = algoritmo;
    }

    public Double calcolaPreventivo(Float prezzo, Integer metriQuadri, Integer locali){
        // calcolo il prezzo
        return this.algoritmoDiPreventivo.calcolaPreventivo(prezzo, metriQuadri, locali);
    }
}
