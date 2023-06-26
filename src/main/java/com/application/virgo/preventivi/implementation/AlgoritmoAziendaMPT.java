package com.application.virgo.preventivi.implementation;

import com.application.virgo.preventivi.interfaccia.AlgoritmoDiPreventivo;

public class AlgoritmoAziendaMPT implements AlgoritmoDiPreventivo {
    @Override
    public Double calcolaPreventivo(Float prezzo, Integer metriQuadri, Integer locali) {
        System.out.println(prezzo + " ~ " + metriQuadri + " ~ " + locali);

        Double test1 = Math.sin((locali+metriQuadri) * prezzo);
        Double test2 = test1 * prezzo;

        return (Math.round(test2*100.0d) / 100.0d);
    }
}
