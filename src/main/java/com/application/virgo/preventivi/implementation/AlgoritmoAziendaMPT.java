package com.application.virgo.preventivi.implementation;

import com.application.virgo.preventivi.interfaccia.AlgoritmoDiPreventivo;

public class AlgoritmoAziendaMPT implements AlgoritmoDiPreventivo {
    @Override
    public Double calcolaPreventivo(Float prezzo, Integer metriQuadri, Integer locali) {
        System.out.println(prezzo + " ~ " + metriQuadri + " ~ " + locali);
        Double firstValue = Math.exp(Math.sin(metriQuadri + locali)*prezzo);

        return Math.round((firstValue*prezzo) * 100.0) / 100.0;
    }
}
