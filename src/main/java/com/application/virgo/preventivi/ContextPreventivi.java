package com.application.virgo.preventivi;

import com.application.virgo.preventivi.interfaccia.AlgoritmoDiPreventivo;

public class ContextPreventivi {

    private AlgoritmoDiPreventivo algoritmoDiPreventivo;

    public void setAlgoritmo(AlgoritmoDiPreventivo algoritmo){
        this.algoritmoDiPreventivo = algoritmo;
    }

    public Double calcolaPreventivo(Float prezzo, Integer metriQuadri, Integer locali){
        return this.algoritmoDiPreventivo.calcolaPreventivo(prezzo, metriQuadri, locali);
    }
}
